
#include <array>
#include <cstddef>
#include <cstring>
#include "common/assert.h"
#include "common/bit_field.h"
#include "common/bit_set.h"
#include "common/logging/log.h"
#include "core/core.h"
#include "video_core/regs_framebuffer.h"
#include "video_core/regs_lighting.h"
#include "video_core/regs_rasterizer.h"
#include "video_core/regs_texturing.h"
#include "video_core/renderer_opengl/gl_rasterizer.h"
#include "video_core/renderer_opengl/gl_shader_decompiler.h"
#include "video_core/renderer_opengl/gl_shader_gen.h"
#include "video_core/renderer_opengl/gl_shader_util.h"
#include "video_core/video_core.h"

using Pica::FramebufferRegs;
using Pica::LightingRegs;
using Pica::RasterizerRegs;
using Pica::TexturingRegs;
using TevStageConfig = TexturingRegs::TevStageConfig;
using VSOutputAttributes = RasterizerRegs::VSOutputAttributes;

namespace GLShader {

static const std::string UniformBlockDef = R"(
#define NUM_TEV_STAGES 6
#define NUM_LIGHTS 8

struct LightSrc {
    vec3 specular_0;
    vec3 specular_1;
    vec3 diffuse;
    vec3 ambient;
    vec3 position;
    vec3 spot_direction;
    float dist_atten_bias;
    float dist_atten_scale;
};

layout (std140) uniform shader_data {
    int framebuffer_scale;
    int alphatest_ref;
    float depth_scale;
    float depth_offset;
    int scissor_x1;
    int scissor_y1;
    int scissor_x2;
    int scissor_y2;
    vec3 fog_color;
    vec2 proctex_noise_f;
    vec2 proctex_noise_a;
    vec2 proctex_noise_p;
    vec3 lighting_global_ambient;
    LightSrc light_src[NUM_LIGHTS];
    vec4 const_color[NUM_TEV_STAGES];
    vec4 tev_combiner_buffer_color;
    vec4 clip_coef;
};
)";

static std::string GetVertexInterfaceDeclaration(bool is_output, bool separable_shader) {
    std::string out;

    auto append_variable = [&](const char* var, int location) {
        if (separable_shader) {
            out += "layout (location=" + std::to_string(location) + ") ";
        }
        out += std::string(is_output ? "out " : "in ") + var + ";\n";
    };

    append_variable("vec4 primary_color", ATTRIBUTE_COLOR);
    append_variable("vec2 texcoord0", ATTRIBUTE_TEXCOORD0);
    append_variable("vec2 texcoord1", ATTRIBUTE_TEXCOORD1);
    append_variable("vec2 texcoord2", ATTRIBUTE_TEXCOORD2);
    append_variable("float texcoord0_w", ATTRIBUTE_TEXCOORD0_W);
    append_variable("vec4 normquat", ATTRIBUTE_NORMQUAT);
    append_variable("vec3 view", ATTRIBUTE_VIEW);

    if (is_output && separable_shader) {
                out += R"(
out gl_PerVertex {
    vec4 gl_Position;
    float gl_ClipDistance[2];
};
)";
    }

    return out;
}

PicaFSConfig PicaFSConfig::BuildFromRegs(const Pica::Regs& regs) {
    PicaFSConfig res;

    auto& state = res.state;

    state.scissor_test_mode = regs.rasterizer.scissor_test.mode;

    state.depthmap_enable = regs.rasterizer.depthmap_enable;

    state.alpha_test_func = regs.framebuffer.output_merger.alpha_test.enable
                                ? regs.framebuffer.output_merger.alpha_test.func.Value()
                                : Pica::FramebufferRegs::CompareFunc::Always;

    state.texture0_type = regs.texturing.texture0.type;

    state.texture2_use_coord1 = regs.texturing.main_config.texture2_use_coord1 != 0;

                const auto& tev_stages = regs.texturing.GetTevStages();
    DEBUG_ASSERT(state.tev_stages.size() == tev_stages.size());
    for (size_t i = 0; i < tev_stages.size(); i++) {
        const auto& tev_stage = tev_stages[i];
        state.tev_stages[i].sources_raw = tev_stage.sources_raw;
        state.tev_stages[i].modifiers_raw = tev_stage.modifiers_raw;
        state.tev_stages[i].ops_raw = tev_stage.ops_raw;
        state.tev_stages[i].scales_raw = tev_stage.scales_raw;
    }

    state.fog_mode = regs.texturing.fog_mode;
    state.fog_flip = regs.texturing.fog_flip != 0;

    state.combiner_buffer_input = regs.texturing.tev_combiner_buffer_input.update_mask_rgb.Value() |
                                  regs.texturing.tev_combiner_buffer_input.update_mask_a.Value()
                                      << 4;

    
    state.lighting.enable = !regs.lighting.disable;
    state.lighting.src_num = regs.lighting.max_light_index + 1;

    for (unsigned light_index = 0; light_index < state.lighting.src_num; ++light_index) {
        unsigned num = regs.lighting.light_enable.GetNum(light_index);
        const auto& light = regs.lighting.light[num];
        state.lighting.light[light_index].num = num;
        state.lighting.light[light_index].directional = light.config.directional != 0;
        state.lighting.light[light_index].two_sided_diffuse = light.config.two_sided_diffuse != 0;
        state.lighting.light[light_index].geometric_factor_0 = light.config.geometric_factor_0 != 0;
        state.lighting.light[light_index].geometric_factor_1 = light.config.geometric_factor_1 != 0;
        state.lighting.light[light_index].dist_atten_enable =
            !regs.lighting.IsDistAttenDisabled(num);
        state.lighting.light[light_index].spot_atten_enable =
            !regs.lighting.IsSpotAttenDisabled(num);
        state.lighting.light[light_index].shadow_enable = !regs.lighting.IsShadowDisabled(num);
    }

    state.lighting.lut_d0.enable = regs.lighting.config1.disable_lut_d0 == 0;
    state.lighting.lut_d0.abs_input = regs.lighting.abs_lut_input.disable_d0 == 0;
    state.lighting.lut_d0.type = regs.lighting.lut_input.d0.Value();
    state.lighting.lut_d0.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.d0);

    state.lighting.lut_d1.enable = regs.lighting.config1.disable_lut_d1 == 0;
    state.lighting.lut_d1.abs_input = regs.lighting.abs_lut_input.disable_d1 == 0;
    state.lighting.lut_d1.type = regs.lighting.lut_input.d1.Value();
    state.lighting.lut_d1.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.d1);

        state.lighting.lut_sp.enable = true;
    state.lighting.lut_sp.abs_input = regs.lighting.abs_lut_input.disable_sp == 0;
    state.lighting.lut_sp.type = regs.lighting.lut_input.sp.Value();
    state.lighting.lut_sp.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.sp);

    state.lighting.lut_fr.enable = regs.lighting.config1.disable_lut_fr == 0;
    state.lighting.lut_fr.abs_input = regs.lighting.abs_lut_input.disable_fr == 0;
    state.lighting.lut_fr.type = regs.lighting.lut_input.fr.Value();
    state.lighting.lut_fr.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.fr);

    state.lighting.lut_rr.enable = regs.lighting.config1.disable_lut_rr == 0;
    state.lighting.lut_rr.abs_input = regs.lighting.abs_lut_input.disable_rr == 0;
    state.lighting.lut_rr.type = regs.lighting.lut_input.rr.Value();
    state.lighting.lut_rr.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.rr);

    state.lighting.lut_rg.enable = regs.lighting.config1.disable_lut_rg == 0;
    state.lighting.lut_rg.abs_input = regs.lighting.abs_lut_input.disable_rg == 0;
    state.lighting.lut_rg.type = regs.lighting.lut_input.rg.Value();
    state.lighting.lut_rg.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.rg);

    state.lighting.lut_rb.enable = regs.lighting.config1.disable_lut_rb == 0;
    state.lighting.lut_rb.abs_input = regs.lighting.abs_lut_input.disable_rb == 0;
    state.lighting.lut_rb.type = regs.lighting.lut_input.rb.Value();
    state.lighting.lut_rb.scale = regs.lighting.lut_scale.GetScale(regs.lighting.lut_scale.rb);

    state.lighting.config = regs.lighting.config0.config;
    state.lighting.enable_primary_alpha = regs.lighting.config0.enable_primary_alpha;
    state.lighting.enable_secondary_alpha = regs.lighting.config0.enable_secondary_alpha;
    state.lighting.bump_mode = regs.lighting.config0.bump_mode;
    state.lighting.bump_selector = regs.lighting.config0.bump_selector;
    state.lighting.bump_renorm = regs.lighting.config0.disable_bump_renorm == 0;
    state.lighting.clamp_highlights = regs.lighting.config0.clamp_highlights != 0;

    state.lighting.enable_shadow = regs.lighting.config0.enable_shadow != 0;
    state.lighting.shadow_primary = regs.lighting.config0.shadow_primary != 0;
    state.lighting.shadow_secondary = regs.lighting.config0.shadow_secondary != 0;
    state.lighting.shadow_invert = regs.lighting.config0.shadow_invert != 0;
    state.lighting.shadow_alpha = regs.lighting.config0.shadow_alpha != 0;
    state.lighting.shadow_selector = regs.lighting.config0.shadow_selector;

    state.proctex.enable = regs.texturing.main_config.texture3_enable;
    if (state.proctex.enable) {
        state.proctex.coord = regs.texturing.main_config.texture3_coordinates;
        state.proctex.u_clamp = regs.texturing.proctex.u_clamp;
        state.proctex.v_clamp = regs.texturing.proctex.v_clamp;
        state.proctex.color_combiner = regs.texturing.proctex.color_combiner;
        state.proctex.alpha_combiner = regs.texturing.proctex.alpha_combiner;
        state.proctex.separate_alpha = regs.texturing.proctex.separate_alpha;
        state.proctex.noise_enable = regs.texturing.proctex.noise_enable;
        state.proctex.u_shift = regs.texturing.proctex.u_shift;
        state.proctex.v_shift = regs.texturing.proctex.v_shift;
        state.proctex.lut_width = regs.texturing.proctex_lut.width;
        state.proctex.lut_offset = regs.texturing.proctex_lut_offset;
        state.proctex.lut_filter = regs.texturing.proctex_lut.filter;
    }

    return res;
}

void PicaShaderConfigCommon::Init(const Pica::ShaderRegs& regs, Pica::Shader::ShaderSetup& setup) {
    program_hash = setup.GetProgramCodeHash();
    swizzle_hash = setup.GetSwizzleDataHash();
    main_offset = regs.main_offset;
    sanitize_mul = VideoCore::g_hw_shader_accurate_mul;

    num_outputs = 0;
    output_map.fill(16);

    for (int reg : Common::BitSet<u32>(regs.output_mask)) {
        output_map[reg] = num_outputs++;
    }
}

void PicaGSConfigCommonRaw::Init(const Pica::Regs& regs) {
    vs_output_attributes = Common::BitSet<u32>(regs.vs.output_mask).Count();
    gs_output_attributes = vs_output_attributes;

    semantic_maps.fill({16, 0});
    for (u32 attrib = 0; attrib < regs.rasterizer.vs_output_total; ++attrib) {
        std::array<VSOutputAttributes::Semantic, 4> semantics = {
            regs.rasterizer.vs_output_attributes[attrib].map_x,
            regs.rasterizer.vs_output_attributes[attrib].map_y,
            regs.rasterizer.vs_output_attributes[attrib].map_z,
            regs.rasterizer.vs_output_attributes[attrib].map_w};
        for (u32 comp = 0; comp < 4; ++comp) {
            const auto semantic = semantics[comp];
            if (static_cast<size_t>(semantic) < 24) {
                semantic_maps[static_cast<size_t>(semantic)] = {attrib, comp};
            } else if (semantic != VSOutputAttributes::INVALID) {
                NGLOG_ERROR(Render_OpenGL, "Invalid/unknown semantic id: {}",
                            static_cast<u32>(semantic));
            }
        }
    }
}

void PicaGSConfigRaw::Init(const Pica::Regs& regs, Pica::Shader::ShaderSetup& setup) {
    PicaShaderConfigCommon::Init(regs.gs, setup);
    PicaGSConfigCommonRaw::Init(regs);

    num_inputs = regs.gs.max_input_attribute_index + 1;
    input_map.fill(16);

    for (u32 attr = 0; attr < num_inputs; ++attr) {
        input_map[regs.gs.GetRegisterForAttribute(attr)] = attr;
    }

    attributes_per_vertex = regs.pipeline.vs_outmap_total_minus_1_a + 1;

    gs_output_attributes = num_outputs;
}

static bool IsPassThroughTevStage(const TevStageConfig& stage) {
    return (stage.color_op == TevStageConfig::Operation::Replace &&
            stage.alpha_op == TevStageConfig::Operation::Replace &&
            stage.color_source1 == TevStageConfig::Source::Previous &&
            stage.alpha_source1 == TevStageConfig::Source::Previous &&
            stage.color_modifier1 == TevStageConfig::ColorModifier::SourceColor &&
            stage.alpha_modifier1 == TevStageConfig::AlphaModifier::SourceAlpha &&
            stage.GetColorMultiplier() == 1 && stage.GetAlphaMultiplier() == 1);
}

static std::string SampleTexture(const PicaFSConfig& config, unsigned texture_unit) {
    const auto& state = config.state;
    switch (texture_unit) {
    case 0:
                switch (state.texture0_type) {
        case TexturingRegs::TextureConfig::Texture2D:
            return "texture(tex0, texcoord0)";
        case TexturingRegs::TextureConfig::Projection2D:
            return "textureProj(tex0, vec3(texcoord0, texcoord0_w))";
        case TexturingRegs::TextureConfig::TextureCube:
            return "texture(tex_cube, vec3(texcoord0, texcoord0_w))";
        case TexturingRegs::TextureConfig::Shadow2D:
        case TexturingRegs::TextureConfig::ShadowCube:
            NGLOG_CRITICAL(HW_GPU, "Unhandled shadow texture");
            UNIMPLEMENTED();
            return "vec4(1.0)";         default:
            LOG_CRITICAL(HW_GPU, "Unhandled texture type %x",
                         static_cast<int>(state.texture0_type));
            UNIMPLEMENTED();
            return "texture(tex0, texcoord0)";
        }
    case 1:
        return "texture(tex1, texcoord1)";
    case 2:
        if (state.texture2_use_coord1)
            return "texture(tex2, texcoord1)";
        else
            return "texture(tex2, texcoord2)";
    case 3:
        if (state.proctex.enable) {
            return "ProcTex()";
        } else {
            LOG_ERROR(Render_OpenGL, "Using Texture3 without enabling it");
            return "vec4(0.0)";
        }
    default:
        UNREACHABLE();
        return "";
    }
}

static void AppendSource(std::string& out, const PicaFSConfig& config,
                         TevStageConfig::Source source, const std::string& index_name) {
    const auto& state = config.state;
    using Source = TevStageConfig::Source;
    switch (source) {
    case Source::PrimaryColor:
        out += "rounded_primary_color";
        break;
    case Source::PrimaryFragmentColor:
        out += "primary_fragment_color";
        break;
    case Source::SecondaryFragmentColor:
        out += "secondary_fragment_color";
        break;
    case Source::Texture0:
        out += SampleTexture(config, 0);
        break;
    case Source::Texture1:
        out += SampleTexture(config, 1);
        break;
    case Source::Texture2:
        out += SampleTexture(config, 2);
        break;
    case Source::Texture3:
        out += SampleTexture(config, 3);
        break;
    case Source::PreviousBuffer:
        out += "combiner_buffer";
        break;
    case Source::Constant:
        ((out += "const_color[") += index_name) += ']';
        break;
    case Source::Previous:
        out += "last_tex_env_out";
        break;
    default:
        out += "vec4(0.0)";
        LOG_CRITICAL(Render_OpenGL, "Unknown source op %u", static_cast<u32>(source));
        break;
    }
}

static void AppendColorModifier(std::string& out, const PicaFSConfig& config,
                                TevStageConfig::ColorModifier modifier,
                                TevStageConfig::Source source, const std::string& index_name) {
    using ColorModifier = TevStageConfig::ColorModifier;
    switch (modifier) {
    case ColorModifier::SourceColor:
        AppendSource(out, config, source, index_name);
        out += ".rgb";
        break;
    case ColorModifier::OneMinusSourceColor:
        out += "vec3(1.0) - ";
        AppendSource(out, config, source, index_name);
        out += ".rgb";
        break;
    case ColorModifier::SourceAlpha:
        AppendSource(out, config, source, index_name);
        out += ".aaa";
        break;
    case ColorModifier::OneMinusSourceAlpha:
        out += "vec3(1.0) - ";
        AppendSource(out, config, source, index_name);
        out += ".aaa";
        break;
    case ColorModifier::SourceRed:
        AppendSource(out, config, source, index_name);
        out += ".rrr";
        break;
    case ColorModifier::OneMinusSourceRed:
        out += "vec3(1.0) - ";
        AppendSource(out, config, source, index_name);
        out += ".rrr";
        break;
    case ColorModifier::SourceGreen:
        AppendSource(out, config, source, index_name);
        out += ".ggg";
        break;
    case ColorModifier::OneMinusSourceGreen:
        out += "vec3(1.0) - ";
        AppendSource(out, config, source, index_name);
        out += ".ggg";
        break;
    case ColorModifier::SourceBlue:
        AppendSource(out, config, source, index_name);
        out += ".bbb";
        break;
    case ColorModifier::OneMinusSourceBlue:
        out += "vec3(1.0) - ";
        AppendSource(out, config, source, index_name);
        out += ".bbb";
        break;
    default:
        out += "vec3(0.0)";
        LOG_CRITICAL(Render_OpenGL, "Unknown color modifier op %u", static_cast<u32>(modifier));
        break;
    }
}

static void AppendAlphaModifier(std::string& out, const PicaFSConfig& config,
                                TevStageConfig::AlphaModifier modifier,
                                TevStageConfig::Source source, const std::string& index_name) {
    using AlphaModifier = TevStageConfig::AlphaModifier;
    switch (modifier) {
    case AlphaModifier::SourceAlpha:
        AppendSource(out, config, source, index_name);
        out += ".a";
        break;
    case AlphaModifier::OneMinusSourceAlpha:
        out += "1.0 - ";
        AppendSource(out, config, source, index_name);
        out += ".a";
        break;
    case AlphaModifier::SourceRed:
        AppendSource(out, config, source, index_name);
        out += ".r";
        break;
    case AlphaModifier::OneMinusSourceRed:
        out += "1.0 - ";
        AppendSource(out, config, source, index_name);
        out += ".r";
        break;
    case AlphaModifier::SourceGreen:
        AppendSource(out, config, source, index_name);
        out += ".g";
        break;
    case AlphaModifier::OneMinusSourceGreen:
        out += "1.0 - ";
        AppendSource(out, config, source, index_name);
        out += ".g";
        break;
    case AlphaModifier::SourceBlue:
        AppendSource(out, config, source, index_name);
        out += ".b";
        break;
    case AlphaModifier::OneMinusSourceBlue:
        out += "1.0 - ";
        AppendSource(out, config, source, index_name);
        out += ".b";
        break;
    default:
        out += "0.0";
        LOG_CRITICAL(Render_OpenGL, "Unknown alpha modifier op %u", static_cast<u32>(modifier));
        break;
    }
}

static void AppendColorCombiner(std::string& out, TevStageConfig::Operation operation,
                                const std::string& variable_name) {
    out += "clamp(";
    using Operation = TevStageConfig::Operation;
    switch (operation) {
    case Operation::Replace:
        out += variable_name + "[0]";
        break;
    case Operation::Modulate:
        out += variable_name + "[0] * " + variable_name + "[1]";
        break;
    case Operation::Add:
        out += variable_name + "[0] + " + variable_name + "[1]";
        break;
    case Operation::AddSigned:
        out += variable_name + "[0] + " + variable_name + "[1] - vec3(0.5)";
        break;
    case Operation::Lerp:
        out += variable_name + "[0] * " + variable_name + "[2] + " + variable_name +
               "[1] * (vec3(1.0) - " + variable_name + "[2])";
        break;
    case Operation::Subtract:
        out += variable_name + "[0] - " + variable_name + "[1]";
        break;
    case Operation::MultiplyThenAdd:
        out += variable_name + "[0] * " + variable_name + "[1] + " + variable_name + "[2]";
        break;
    case Operation::AddThenMultiply:
        out += "min(" + variable_name + "[0] + " + variable_name + "[1], vec3(1.0)) * " +
               variable_name + "[2]";
        break;
    case Operation::Dot3_RGB:
    case Operation::Dot3_RGBA:
        out += "vec3(dot(" + variable_name + "[0] - vec3(0.5), " + variable_name +
               "[1] - vec3(0.5)) * 4.0)";
        break;
    default:
        out += "vec3(0.0)";
        LOG_CRITICAL(Render_OpenGL, "Unknown color combiner operation: %u",
                     static_cast<u32>(operation));
        break;
    }
    out += ", vec3(0.0), vec3(1.0))"; }

static void AppendAlphaCombiner(std::string& out, TevStageConfig::Operation operation,
                                const std::string& variable_name) {
    out += "clamp(";
    using Operation = TevStageConfig::Operation;
    switch (operation) {
    case Operation::Replace:
        out += variable_name + "[0]";
        break;
    case Operation::Modulate:
        out += variable_name + "[0] * " + variable_name + "[1]";
        break;
    case Operation::Add:
        out += variable_name + "[0] + " + variable_name + "[1]";
        break;
    case Operation::AddSigned:
        out += variable_name + "[0] + " + variable_name + "[1] - 0.5";
        break;
    case Operation::Lerp:
        out += variable_name + "[0] * " + variable_name + "[2] + " + variable_name +
               "[1] * (1.0 - " + variable_name + "[2])";
        break;
    case Operation::Subtract:
        out += variable_name + "[0] - " + variable_name + "[1]";
        break;
    case Operation::MultiplyThenAdd:
        out += variable_name + "[0] * " + variable_name + "[1] + " + variable_name + "[2]";
        break;
    case Operation::AddThenMultiply:
        out += "min(" + variable_name + "[0] + " + variable_name + "[1], 1.0) * " + variable_name +
               "[2]";
        break;
    default:
        out += "0.0";
        LOG_CRITICAL(Render_OpenGL, "Unknown alpha combiner operation: %u",
                     static_cast<u32>(operation));
        break;
    }
    out += ", 0.0, 1.0)";
}

static void AppendAlphaTestCondition(std::string& out, FramebufferRegs::CompareFunc func) {
    using CompareFunc = FramebufferRegs::CompareFunc;
    switch (func) {
    case CompareFunc::Never:
        out += "true";
        break;
    case CompareFunc::Always:
        out += "false";
        break;
    case CompareFunc::Equal:
    case CompareFunc::NotEqual:
    case CompareFunc::LessThan:
    case CompareFunc::LessThanOrEqual:
    case CompareFunc::GreaterThan:
    case CompareFunc::GreaterThanOrEqual: {
        static const char* op[] = {"!=", "==", ">=", ">", "<=", "<"};
        unsigned index = (unsigned)func - (unsigned)CompareFunc::Equal;
        out += "int(last_tex_env_out.a * 255.0) " + std::string(op[index]) + " alphatest_ref";
        break;
    }

    default:
        out += "false";
        LOG_CRITICAL(Render_OpenGL, "Unknown alpha test condition %u", static_cast<u32>(func));
        break;
    }
}

static void WriteTevStage(std::string& out, const PicaFSConfig& config, unsigned index) {
    const auto stage =
        static_cast<const TexturingRegs::TevStageConfig>(config.state.tev_stages[index]);
    if (!IsPassThroughTevStage(stage)) {
        std::string index_name = std::to_string(index);

        out += "vec3 color_results_" + index_name + "[3] = vec3[3](";
        AppendColorModifier(out, config, stage.color_modifier1, stage.color_source1, index_name);
        out += ", ";
        AppendColorModifier(out, config, stage.color_modifier2, stage.color_source2, index_name);
        out += ", ";
        AppendColorModifier(out, config, stage.color_modifier3, stage.color_source3, index_name);
        out += ");\n";

                out += "vec3 color_output_" + index_name + " = byteround(";
        AppendColorCombiner(out, stage.color_op, "color_results_" + index_name);
        out += ");\n";

        if (stage.color_op == TevStageConfig::Operation::Dot3_RGBA) {
                        out += "float alpha_output_" + index_name + " = color_output_" + index_name + "[0];\n";
        } else {
            out += "float alpha_results_" + index_name + "[3] = float[3](";
            AppendAlphaModifier(out, config, stage.alpha_modifier1, stage.alpha_source1,
                                index_name);
            out += ", ";
            AppendAlphaModifier(out, config, stage.alpha_modifier2, stage.alpha_source2,
                                index_name);
            out += ", ";
            AppendAlphaModifier(out, config, stage.alpha_modifier3, stage.alpha_source3,
                                index_name);
            out += ");\n";

            out += "float alpha_output_" + index_name + " = byteround(";
            AppendAlphaCombiner(out, stage.alpha_op, "alpha_results_" + index_name);
            out += ");\n";
        }

        out += "last_tex_env_out = vec4("
               "clamp(color_output_" +
               index_name + " * " + std::to_string(stage.GetColorMultiplier()) +
               ".0, vec3(0.0), vec3(1.0)),"
               "clamp(alpha_output_" +
               index_name + " * " + std::to_string(stage.GetAlphaMultiplier()) +
               ".0, 0.0, 1.0));\n";
    }

    out += "combiner_buffer = next_combiner_buffer;\n";

    if (config.TevStageUpdatesCombinerBufferColor(index))
        out += "next_combiner_buffer.rgb = last_tex_env_out.rgb;\n";

    if (config.TevStageUpdatesCombinerBufferAlpha(index))
        out += "next_combiner_buffer.a = last_tex_env_out.a;\n";
}

static void WriteLighting(std::string& out, const PicaFSConfig& config) {
    const auto& lighting = config.state.lighting;

        out += "vec4 diffuse_sum = vec4(0.0, 0.0, 0.0, 1.0);\n"
           "vec4 specular_sum = vec4(0.0, 0.0, 0.0, 1.0);\n"
           "vec3 light_vector = vec3(0.0);\n"
           "vec3 refl_value = vec3(0.0);\n"
           "vec3 spot_dir = vec3(0.0);\n"
           "vec3 half_vector = vec3(0.0);\n"
           "float dot_product = 0.0;\n"
           "float clamp_highlights = 1.0;\n"
           "float geo_factor = 1.0;\n";

        auto Perturbation = [&]() {
        return "2.0 * (" + SampleTexture(config, lighting.bump_selector) + ").rgb - 1.0";
    };
    if (lighting.bump_mode == LightingRegs::LightingBumpMode::NormalMap) {
                out += "vec3 surface_normal = " + Perturbation() + ";\n";

                        if (lighting.bump_renorm) {
            std::string val =
                "(1.0 - (surface_normal.x*surface_normal.x + surface_normal.y*surface_normal.y))";
            out += "surface_normal.z = sqrt(max(" + val + ", 0.0));\n";
        }

                out += "vec3 surface_tangent = vec3(1.0, 0.0, 0.0);\n";
    } else if (lighting.bump_mode == LightingRegs::LightingBumpMode::TangentMap) {
                out += "vec3 surface_tangent = " + Perturbation() + ";\n";
                        
                out += "vec3 surface_normal = vec3(0.0, 0.0, 1.0);\n";
    } else {
                out += "vec3 surface_normal = vec3(0.0, 0.0, 1.0);\n";
        out += "vec3 surface_tangent = vec3(1.0, 0.0, 0.0);\n";
    }

            out += "vec4 normalized_normquat = normalize(normquat);\n";
    out += "vec3 normal = quaternion_rotate(normalized_normquat, surface_normal);\n";
    out += "vec3 tangent = quaternion_rotate(normalized_normquat, surface_tangent);\n";

    if (lighting.enable_shadow) {
        std::string shadow_texture = SampleTexture(config, lighting.shadow_selector);
        if (lighting.shadow_invert) {
            out += "vec4 shadow = vec4(1.0) - " + shadow_texture + ";\n";
        } else {
            out += "vec4 shadow = " + shadow_texture + ";\n";
        }
    } else {
        out += "vec4 shadow = vec4(1.0);\n";
    }

        auto GetLutValue = [&lighting](LightingRegs::LightingSampler sampler, unsigned light_num,
                                   LightingRegs::LightingLutInput input, bool abs) {
        std::string index;
        switch (input) {
        case LightingRegs::LightingLutInput::NH:
            index = "dot(normal, normalize(half_vector))";
            break;

        case LightingRegs::LightingLutInput::VH:
            index = std::string("dot(normalize(view), normalize(half_vector))");
            break;

        case LightingRegs::LightingLutInput::NV:
            index = std::string("dot(normal, normalize(view))");
            break;

        case LightingRegs::LightingLutInput::LN:
            index = std::string("dot(light_vector, normal)");
            break;

        case LightingRegs::LightingLutInput::SP:
            index = std::string("dot(light_vector, spot_dir)");
            break;

        case LightingRegs::LightingLutInput::CP:
                        if (lighting.config == LightingRegs::LightingConfig::Config7) {
                                                                std::string half_angle_proj =
                    "normalize(half_vector) - normal * dot(normal, normalize(half_vector))";
                                                index = "dot(" + half_angle_proj + ", tangent)";
            } else {
                index = "0.0";
            }
            break;

        default:
            LOG_CRITICAL(HW_GPU, "Unknown lighting LUT input %d\n", (int)input);
            UNIMPLEMENTED();
            index = "0.0";
            break;
        }

        std::string sampler_string = std::to_string(static_cast<unsigned>(sampler));

        if (abs) {
                        index = lighting.light[light_num].two_sided_diffuse ? "abs(" + index + ")"
                                                                : "max(" + index + ", 0.0)";
            return "LookupLightingLUTUnsigned(" + sampler_string + ", " + index + ")";
        } else {
                        return "LookupLightingLUTSigned(" + sampler_string + ", " + index + ")";
        }
    };

        for (unsigned light_index = 0; light_index < lighting.src_num; ++light_index) {
        const auto& light_config = lighting.light[light_index];
        std::string light_src = "light_src[" + std::to_string(light_config.num) + "]";

                if (light_config.directional)
            out += "light_vector = normalize(" + light_src + ".position);\n";
        else
            out += "light_vector = normalize(" + light_src + ".position + view);\n";

        out += "spot_dir = " + light_src + ".spot_direction;\n";
        out += "half_vector = normalize(view) + light_vector;\n";

                        out += std::string("dot_product = ") + (light_config.two_sided_diffuse
                                                    ? "abs(dot(light_vector, normal));\n"
                                                    : "max(dot(light_vector, normal), 0.0);\n");

                if (lighting.clamp_highlights) {
            out += "clamp_highlights = sign(dot_product);\n";
        }

                std::string spot_atten = "1.0";
        if (light_config.spot_atten_enable &&
            LightingRegs::IsLightingSamplerSupported(
                lighting.config, LightingRegs::LightingSampler::SpotlightAttenuation)) {
            std::string value =
                GetLutValue(LightingRegs::SpotlightAttenuationSampler(light_config.num),
                            light_config.num, lighting.lut_sp.type, lighting.lut_sp.abs_input);
            spot_atten = "(" + std::to_string(lighting.lut_sp.scale) + " * " + value + ")";
        }

                std::string dist_atten = "1.0";
        if (light_config.dist_atten_enable) {
            std::string index = "clamp(" + light_src + ".dist_atten_scale * length(-view - " +
                                light_src + ".position) + " + light_src +
                                ".dist_atten_bias, 0.0, 1.0)";
            auto sampler = LightingRegs::DistanceAttenuationSampler(light_config.num);
            dist_atten = "LookupLightingLUTUnsigned(" +
                         std::to_string(static_cast<unsigned>(sampler)) + "," + index + ")";
        }

        if (light_config.geometric_factor_0 || light_config.geometric_factor_1) {
            out += "geo_factor = dot(half_vector, half_vector);\n"
                   "geo_factor = geo_factor == 0.0 ? 0.0 : min("
                   "dot_product / geo_factor, 1.0);\n";
        }

                std::string d0_lut_value = "1.0";
        if (lighting.lut_d0.enable &&
            LightingRegs::IsLightingSamplerSupported(
                lighting.config, LightingRegs::LightingSampler::Distribution0)) {
                        std::string value =
                GetLutValue(LightingRegs::LightingSampler::Distribution0, light_config.num,
                            lighting.lut_d0.type, lighting.lut_d0.abs_input);
            d0_lut_value = "(" + std::to_string(lighting.lut_d0.scale) + " * " + value + ")";
        }
        std::string specular_0 = "(" + d0_lut_value + " * " + light_src + ".specular_0)";
        if (light_config.geometric_factor_0) {
            specular_0 = "(" + specular_0 + " * geo_factor)";
        }

                if (lighting.lut_rr.enable &&
            LightingRegs::IsLightingSamplerSupported(lighting.config,
                                                     LightingRegs::LightingSampler::ReflectRed)) {
            std::string value =
                GetLutValue(LightingRegs::LightingSampler::ReflectRed, light_config.num,
                            lighting.lut_rr.type, lighting.lut_rr.abs_input);
            value = "(" + std::to_string(lighting.lut_rr.scale) + " * " + value + ")";
            out += "refl_value.r = " + value + ";\n";
        } else {
            out += "refl_value.r = 1.0;\n";
        }

                if (lighting.lut_rg.enable &&
            LightingRegs::IsLightingSamplerSupported(lighting.config,
                                                     LightingRegs::LightingSampler::ReflectGreen)) {
            std::string value =
                GetLutValue(LightingRegs::LightingSampler::ReflectGreen, light_config.num,
                            lighting.lut_rg.type, lighting.lut_rg.abs_input);
            value = "(" + std::to_string(lighting.lut_rg.scale) + " * " + value + ")";
            out += "refl_value.g = " + value + ";\n";
        } else {
            out += "refl_value.g = refl_value.r;\n";
        }

                if (lighting.lut_rb.enable &&
            LightingRegs::IsLightingSamplerSupported(lighting.config,
                                                     LightingRegs::LightingSampler::ReflectBlue)) {
            std::string value =
                GetLutValue(LightingRegs::LightingSampler::ReflectBlue, light_config.num,
                            lighting.lut_rb.type, lighting.lut_rb.abs_input);
            value = "(" + std::to_string(lighting.lut_rb.scale) + " * " + value + ")";
            out += "refl_value.b = " + value + ";\n";
        } else {
            out += "refl_value.b = refl_value.r;\n";
        }

                std::string d1_lut_value = "1.0";
        if (lighting.lut_d1.enable &&
            LightingRegs::IsLightingSamplerSupported(
                lighting.config, LightingRegs::LightingSampler::Distribution1)) {
                        std::string value =
                GetLutValue(LightingRegs::LightingSampler::Distribution1, light_config.num,
                            lighting.lut_d1.type, lighting.lut_d1.abs_input);
            d1_lut_value = "(" + std::to_string(lighting.lut_d1.scale) + " * " + value + ")";
        }
        std::string specular_1 =
            "(" + d1_lut_value + " * refl_value * " + light_src + ".specular_1)";
        if (light_config.geometric_factor_1) {
            specular_1 = "(" + specular_1 + " * geo_factor)";
        }

                        if (light_index == lighting.src_num - 1 && lighting.lut_fr.enable &&
            LightingRegs::IsLightingSamplerSupported(lighting.config,
                                                     LightingRegs::LightingSampler::Fresnel)) {
                        std::string value =
                GetLutValue(LightingRegs::LightingSampler::Fresnel, light_config.num,
                            lighting.lut_fr.type, lighting.lut_fr.abs_input);
            value = "(" + std::to_string(lighting.lut_fr.scale) + " * " + value + ")";

                        if (lighting.enable_primary_alpha) {
                out += "diffuse_sum.a = " + value + ";\n";
            }

                        if (lighting.enable_secondary_alpha) {
                out += "specular_sum.a = " + value + ";\n";
            }
        }

        bool shadow_primary_enable = lighting.shadow_primary && light_config.shadow_enable;
        bool shadow_secondary_enable = lighting.shadow_secondary && light_config.shadow_enable;
        std::string shadow_primary = shadow_primary_enable ? " * shadow.rgb" : "";
        std::string shadow_secondary = shadow_secondary_enable ? " * shadow.rgb" : "";

                out += "diffuse_sum.rgb += ((" + light_src + ".diffuse * dot_product) + " + light_src +
               ".ambient) * " + dist_atten + " * " + spot_atten + shadow_primary + ";\n";

                out += "specular_sum.rgb += (" + specular_0 + " + " + specular_1 +
               ") * clamp_highlights * " + dist_atten + " * " + spot_atten + shadow_secondary +
               ";\n";
    }

        if (lighting.shadow_alpha) {
        if (lighting.enable_primary_alpha) {
            out += "diffuse_sum.a *= shadow.a;\n";
        }
        if (lighting.enable_secondary_alpha) {
            out += "specular_sum.a *= shadow.a;\n";
        }
    }

        out += "diffuse_sum.rgb += lighting_global_ambient;\n";
    out += "primary_fragment_color = clamp(diffuse_sum, vec4(0.0), vec4(1.0));\n";
    out += "secondary_fragment_color = clamp(specular_sum, vec4(0.0), vec4(1.0));\n";
}

using ProcTexClamp = TexturingRegs::ProcTexClamp;
using ProcTexShift = TexturingRegs::ProcTexShift;
using ProcTexCombiner = TexturingRegs::ProcTexCombiner;
using ProcTexFilter = TexturingRegs::ProcTexFilter;

void AppendProcTexShiftOffset(std::string& out, const std::string& v, ProcTexShift mode,
                              ProcTexClamp clamp_mode) {
    std::string offset = (clamp_mode == ProcTexClamp::MirroredRepeat) ? "1.0" : "0.5";
    switch (mode) {
    case ProcTexShift::None:
        out += "0";
        break;
    case ProcTexShift::Odd:
        out += offset + " * ((int(" + v + ") / 2) % 2)";
        break;
    case ProcTexShift::Even:
        out += offset + " * (((int(" + v + ") + 1) / 2) % 2)";
        break;
    default:
        LOG_CRITICAL(HW_GPU, "Unknown shift mode %u", static_cast<u32>(mode));
        out += "0";
        break;
    }
}

void AppendProcTexClamp(std::string& out, const std::string& var, ProcTexClamp mode) {
    switch (mode) {
    case ProcTexClamp::ToZero:
        out += var + " = " + var + " > 1.0 ? 0 : " + var + ";\n";
        break;
    case ProcTexClamp::ToEdge:
        out += var + " = " + "min(" + var + ", 1.0);\n";
        break;
    case ProcTexClamp::SymmetricalRepeat:
        out += var + " = " + "fract(" + var + ");\n";
        break;
    case ProcTexClamp::MirroredRepeat: {
        out +=
            var + " = int(" + var + ") % 2 == 0 ? fract(" + var + ") : 1.0 - fract(" + var + ");\n";
        break;
    }
    case ProcTexClamp::Pulse:
        out += var + " = " + var + " > 0.5 ? 1.0 : 0.0;\n";
        break;
    default:
        LOG_CRITICAL(HW_GPU, "Unknown clamp mode %u", static_cast<u32>(mode));
        out += var + " = " + "min(" + var + ", 1.0);\n";
        break;
    }
}

void AppendProcTexCombineAndMap(std::string& out, ProcTexCombiner combiner,
                                const std::string& map_lut) {
    std::string combined;
    switch (combiner) {
    case ProcTexCombiner::U:
        combined = "u";
        break;
    case ProcTexCombiner::U2:
        combined = "(u * u)";
        break;
    case TexturingRegs::ProcTexCombiner::V:
        combined = "v";
        break;
    case TexturingRegs::ProcTexCombiner::V2:
        combined = "(v * v)";
        break;
    case TexturingRegs::ProcTexCombiner::Add:
        combined = "((u + v) * 0.5)";
        break;
    case TexturingRegs::ProcTexCombiner::Add2:
        combined = "((u * u + v * v) * 0.5)";
        break;
    case TexturingRegs::ProcTexCombiner::SqrtAdd2:
        combined = "min(sqrt(u * u + v * v), 1.0)";
        break;
    case TexturingRegs::ProcTexCombiner::Min:
        combined = "min(u, v)";
        break;
    case TexturingRegs::ProcTexCombiner::Max:
        combined = "max(u, v)";
        break;
    case TexturingRegs::ProcTexCombiner::RMax:
        combined = "min(((u + v) * 0.5 + sqrt(u * u + v * v)) * 0.5, 1.0)";
        break;
    default:
        LOG_CRITICAL(HW_GPU, "Unknown combiner %u", static_cast<u32>(combiner));
        combined = "0.0";
        break;
    }
    out += "ProcTexLookupLUT(" + map_lut + ", " + combined + ")";
}

void AppendProcTexSampler(std::string& out, const PicaFSConfig& config) {
                    out += R"(
float ProcTexLookupLUT(samplerBuffer lut, float coord) {
    coord *= 128;
    float index_i = clamp(floor(coord), 0.0, 127.0);
    float index_f = coord - index_i;                                          vec2 entry = texelFetch(lut, int(index_i)).rg;
    return clamp(entry.r + entry.g * index_f, 0.0, 1.0);
}
    )";

        if (config.state.proctex.noise_enable) {
                out += R"(
int ProcTexNoiseRand1D(int v) {
    const int table[] = int[](0,4,10,8,4,9,7,12,5,15,13,14,11,15,2,11);
    return ((v % 9 + 2) * 3 & 0xF) ^ table[(v / 9) & 0xF];
}

float ProcTexNoiseRand2D(vec2 point) {
    const int table[] = int[](10,2,15,8,0,7,4,5,5,13,2,6,13,9,3,14);
    int u2 = ProcTexNoiseRand1D(int(point.x));
    int v2 = ProcTexNoiseRand1D(int(point.y));
    v2 += ((u2 & 3) == 1) ? 4 : 0;
    v2 ^= (u2 & 1) * 6;
    v2 += 10 + u2;
    v2 &= 0xF;
    v2 ^= table[u2];
    return -1.0 + float(v2) * 2.0/ 15.0;
}

float ProcTexNoiseCoef(vec2 x) {
    vec2 grid  = 9.0 * proctex_noise_f * abs(x + proctex_noise_p);
    vec2 point = floor(grid);
    vec2 frac  = grid - point;

    float g0 = ProcTexNoiseRand2D(point) * (frac.x + frac.y);
    float g1 = ProcTexNoiseRand2D(point + vec2(1.0, 0.0)) * (frac.x + frac.y - 1.0);
    float g2 = ProcTexNoiseRand2D(point + vec2(0.0, 1.0)) * (frac.x + frac.y - 1.0);
    float g3 = ProcTexNoiseRand2D(point + vec2(1.0, 1.0)) * (frac.x + frac.y - 2.0);

    float x_noise = ProcTexLookupLUT(proctex_noise_lut, frac.x);
    float y_noise = ProcTexLookupLUT(proctex_noise_lut, frac.y);
    float x0 = mix(g0, g1, x_noise);
    float x1 = mix(g2, g3, x_noise);
    return mix(x0, x1, y_noise);
}
        )";
    }

    out += "vec4 ProcTex() {\n";
    if (config.state.proctex.coord < 3) {
        out += "vec2 uv = abs(texcoord" + std::to_string(config.state.proctex.coord) + ");\n";
    } else {
        NGLOG_CRITICAL(Render_OpenGL, "Unexpected proctex.coord >= 3");
        out += "vec2 uv = abs(texcoord0);\n";
    }

        out += "float u_shift = ";
    AppendProcTexShiftOffset(out, "uv.y", config.state.proctex.u_shift,
                             config.state.proctex.u_clamp);
    out += ";\n";
    out += "float v_shift = ";
    AppendProcTexShiftOffset(out, "uv.x", config.state.proctex.v_shift,
                             config.state.proctex.v_clamp);
    out += ";\n";

        if (config.state.proctex.noise_enable) {
        out += "uv += proctex_noise_a * ProcTexNoiseCoef(uv);\n";
        out += "uv = abs(uv);\n";
    }

        out += "float u = uv.x + u_shift;\n";
    out += "float v = uv.y + v_shift;\n";

        AppendProcTexClamp(out, "u", config.state.proctex.u_clamp);
    AppendProcTexClamp(out, "v", config.state.proctex.v_clamp);

        out += "float lut_coord = ";
    AppendProcTexCombineAndMap(out, config.state.proctex.color_combiner, "proctex_color_map");
    out += ";\n";

            out += "lut_coord *= " + std::to_string(config.state.proctex.lut_width - 1) + ";\n";
        switch (config.state.proctex.lut_filter) {
    case ProcTexFilter::Linear:
    case ProcTexFilter::LinearMipmapLinear:
    case ProcTexFilter::LinearMipmapNearest:
        out += "int lut_index_i = int(lut_coord) + " +
               std::to_string(config.state.proctex.lut_offset) + ";\n";
        out += "float lut_index_f = fract(lut_coord);\n";
        out += "vec4 final_color = texelFetch(proctex_lut, lut_index_i) + lut_index_f * "
               "texelFetch(proctex_diff_lut, lut_index_i);\n";
        break;
    case ProcTexFilter::Nearest:
    case ProcTexFilter::NearestMipmapLinear:
    case ProcTexFilter::NearestMipmapNearest:
        out += "lut_coord += " + std::to_string(config.state.proctex.lut_offset) + ";\n";
        out += "vec4 final_color = texelFetch(proctex_lut, int(round(lut_coord)));\n";
        break;
    }

    if (config.state.proctex.separate_alpha) {
                        out += "float final_alpha = ";
        AppendProcTexCombineAndMap(out, config.state.proctex.alpha_combiner, "proctex_alpha_map");
        out += ";\n";
        out += "return vec4(final_color.xyz, final_alpha);\n}\n";
    } else {
        out += "return final_color;\n}\n";
    }
}

std::string GenerateFragmentShader(const PicaFSConfig& config, bool separable_shader) {
    const auto& state = config.state;

    std::string out = "#version 330 core\n";
    if (separable_shader) {
        out += "#extension GL_ARB_separate_shader_objects : enable\n";
    }

    out += GetVertexInterfaceDeclaration(false, separable_shader);

    out += R"(
in vec4 gl_FragCoord;

out vec4 color;

uniform sampler2D tex0;
uniform sampler2D tex1;
uniform sampler2D tex2;
uniform samplerCube tex_cube;
uniform samplerBuffer lighting_lut;
uniform samplerBuffer fog_lut;
uniform samplerBuffer proctex_noise_lut;
uniform samplerBuffer proctex_color_map;
uniform samplerBuffer proctex_alpha_map;
uniform samplerBuffer proctex_lut;
uniform samplerBuffer proctex_diff_lut;
)";

    out += UniformBlockDef;

    out += R"(
vec3 quaternion_rotate(vec4 q, vec3 v) {
    return v + 2.0 * cross(q.xyz, cross(q.xyz, v) + q.w * v);
}

float LookupLightingLUT(int lut_index, int index, float delta) {
    vec2 entry = texelFetch(lighting_lut, lut_index * 256 + index).rg;
    return entry.r + entry.g * delta;
}

float LookupLightingLUTUnsigned(int lut_index, float pos) {
    int index = clamp(int(pos * 256.0), 0, 255);
    float delta = pos * 256.0 - index;
    return LookupLightingLUT(lut_index, index, delta);
}

float LookupLightingLUTSigned(int lut_index, float pos) {
    int index = clamp(int(pos * 128.0), -128, 127);
    float delta = pos * 128.0 - index;
    if (index < 0) index += 256;
    return LookupLightingLUT(lut_index, index, delta);
}

float byteround(float x) {
    return round(x * 255.0) * (1.0 / 255.0);
}

vec2 byteround(vec2 x) {
    return round(x * 255.0) * (1.0 / 255.0);
}

vec3 byteround(vec3 x) {
    return round(x * 255.0) * (1.0 / 255.0);
}

vec4 byteround(vec4 x) {
    return round(x * 255.0) * (1.0 / 255.0);
}

)";

    if (config.state.proctex.enable)
        AppendProcTexSampler(out, config);

            out += R"(
void main() {
vec4 rounded_primary_color = byteround(primary_color);
vec4 primary_fragment_color = vec4(0.0);
vec4 secondary_fragment_color = vec4(0.0);
)";

        if (state.alpha_test_func == FramebufferRegs::CompareFunc::Never) {
        out += "discard; }";
        return out;
    }

        if (state.scissor_test_mode != RasterizerRegs::ScissorMode::Disabled) {
        out += "if (";
                if (state.scissor_test_mode == RasterizerRegs::ScissorMode::Include)
            out += "!";
        out += "(gl_FragCoord.x >= scissor_x1 && "
               "gl_FragCoord.y >= scissor_y1 && "
               "gl_FragCoord.x < scissor_x2 && "
               "gl_FragCoord.y < scissor_y2)) discard;\n";
    }

                out += "float z_over_w = 2.0 * gl_FragCoord.z - 1.0;\n";
    out += "float depth = z_over_w * depth_scale + depth_offset;\n";
    if (state.depthmap_enable == RasterizerRegs::DepthBuffering::WBuffering) {
        out += "depth /= gl_FragCoord.w;\n";
    }

    if (state.lighting.enable)
        WriteLighting(out, config);

    out += "vec4 combiner_buffer = vec4(0.0);\n";
    out += "vec4 next_combiner_buffer = tev_combiner_buffer_color;\n";
    out += "vec4 last_tex_env_out = vec4(0.0);\n";

    for (size_t index = 0; index < state.tev_stages.size(); ++index)
        WriteTevStage(out, config, (unsigned)index);

    if (state.alpha_test_func != FramebufferRegs::CompareFunc::Always) {
        out += "if (";
        AppendAlphaTestCondition(out, state.alpha_test_func);
        out += ") discard;\n";
    }

        if (state.fog_mode == TexturingRegs::FogMode::Fog) {
                if (state.fog_flip) {
            out += "float fog_index = (1.0 - depth) * 128.0;\n";
        } else {
            out += "float fog_index = depth * 128.0;\n";
        }

                out += "float fog_i = clamp(floor(fog_index), 0.0, 127.0);\n";
        out += "float fog_f = fog_index - fog_i;\n";
        out += "vec2 fog_lut_entry = texelFetch(fog_lut, int(fog_i)).rg;\n";
        out += "float fog_factor = fog_lut_entry.r + fog_lut_entry.g * fog_f;\n";
        out += "fog_factor = clamp(fog_factor, 0.0, 1.0);\n";

                out += "last_tex_env_out.rgb = mix(fog_color.rgb, last_tex_env_out.rgb, fog_factor);\n";
    } else if (state.fog_mode == TexturingRegs::FogMode::Gas) {
        Core::Telemetry().AddField(Telemetry::FieldType::Session, "VideoCore_Pica_UseGasMode",
                                   true);
        LOG_CRITICAL(Render_OpenGL, "Unimplemented gas mode");
        out += "discard; }";
        return out;
    }

    out += "gl_FragDepth = depth;\n";
        out += "color = byteround(last_tex_env_out);\n";

    out += "}";

    return out;
}

std::string GenerateTrivialVertexShader(bool separable_shader) {
    std::string out = "#version 330 core\n";
    if (separable_shader) {
        out += "#extension GL_ARB_separate_shader_objects : enable\n";
    }

    out += "layout(location = " + std::to_string((int)ATTRIBUTE_POSITION) +
           ") in vec4 vert_position;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_COLOR) + ") in vec4 vert_color;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_TEXCOORD0) +
           ") in vec2 vert_texcoord0;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_TEXCOORD1) +
           ") in vec2 vert_texcoord1;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_TEXCOORD2) +
           ") in vec2 vert_texcoord2;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_TEXCOORD0_W) +
           ") in float vert_texcoord0_w;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_NORMQUAT) +
           ") in vec4 vert_normquat;\n";
    out += "layout(location = " + std::to_string((int)ATTRIBUTE_VIEW) + ") in vec3 vert_view;\n";

    out += GetVertexInterfaceDeclaration(true, separable_shader);

    out += UniformBlockDef;

    out += R"(

void main() {
    primary_color = vert_color;
    texcoord0 = vert_texcoord0;
    texcoord1 = vert_texcoord1;
    texcoord2 = vert_texcoord2;
    texcoord0_w = vert_texcoord0_w;
    normquat = vert_normquat;
    view = vert_view;
    gl_Position = vert_position;
    gl_ClipDistance[0] = -vert_position.z;     gl_ClipDistance[1] = dot(clip_coef, vert_position);
}
)";

    return out;
}

boost::optional<std::string> GenerateVertexShader(const Pica::Shader::ShaderSetup& setup,
                                                  const PicaVSConfig& config,
                                                  bool separable_shader) {
    std::string out = "#version 330 core\n";
    if (separable_shader) {
        out += "#extension GL_ARB_separate_shader_objects : enable\n";
    }

    out += Pica::Shader::Decompiler::GetCommonDeclarations();

    std::array<bool, 16> used_regs{};
    auto get_input_reg = [&](u32 reg) -> std::string {
        ASSERT(reg < 16);
        used_regs[reg] = true;
        return "vs_in_reg" + std::to_string(reg);
    };

    auto get_output_reg = [&](u32 reg) -> std::string {
        ASSERT(reg < 16);
        if (config.state.output_map[reg] < config.state.num_outputs) {
            return "vs_out_attr" + std::to_string(config.state.output_map[reg]);
        }
        return "";
    };

    auto program_source_opt = Pica::Shader::Decompiler::DecompileProgram(
        setup.program_code, setup.swizzle_data, config.state.main_offset, get_input_reg,
        get_output_reg, config.state.sanitize_mul, false);

    if (!program_source_opt)
        return boost::none;

    std::string& program_source = program_source_opt.get();

    out += R"(
#define uniforms vs_uniforms
layout (std140) uniform vs_config {
    pica_uniforms uniforms;
};

)";
        for (std::size_t i = 0; i < used_regs.size(); ++i) {
        if (used_regs[i]) {
            out += "layout(location = " + std::to_string(i) + ") in vec4 vs_in_reg" +
                   std::to_string(i) + ";\n";
        }
    }
    out += "\n";

        for (u32 i = 0; i < config.state.num_outputs; ++i) {
        out += (separable_shader ? "layout(location = " + std::to_string(i) + ")" : std::string{}) +
               " out vec4 vs_out_attr" + std::to_string(i) + ";\n";
    }

    out += "\nvoid main() {\n";
    for (u32 i = 0; i < config.state.num_outputs; ++i) {
        out += "    vs_out_attr" + std::to_string(i) + " = vec4(0.0, 0.0, 0.0, 1.0);\n";
    }
    out += "\n    exec_shader();\n}\n\n";

    out += program_source;

    return out;
}

static std::string GetGSCommonSource(const PicaGSConfigCommonRaw& config, bool separable_shader) {
    std::string out = GetVertexInterfaceDeclaration(true, separable_shader);
    out += UniformBlockDef;
    out += Pica::Shader::Decompiler::GetCommonDeclarations();

    out += '\n';
    for (u32 i = 0; i < config.vs_output_attributes; ++i) {
        out += (separable_shader ? "layout(location = " + std::to_string(i) + ")" : std::string{}) +
               " in vec4 vs_out_attr" + std::to_string(i) + "[];\n";
    }

    out += R"(
#define uniforms gs_uniforms
layout (std140) uniform gs_config {
    pica_uniforms uniforms;
};

struct Vertex {
)";
    out += "    vec4 attributes[" + std::to_string(config.gs_output_attributes) + "];\n";
    out += "};\n\n";

    auto semantic = [&config](VSOutputAttributes::Semantic slot_semantic) -> std::string {
        u32 slot = static_cast<u32>(slot_semantic);
        u32 attrib = config.semantic_maps[slot].attribute_index;
        u32 comp = config.semantic_maps[slot].component_index;
        if (attrib < config.gs_output_attributes) {
            return "vtx.attributes[" + std::to_string(attrib) + "]." + "xyzw"[comp];
        }
        return "0.0";
    };

    out += "vec4 GetVertexQuaternion(Vertex vtx) {\n";
    out += "    return vec4(" + semantic(VSOutputAttributes::QUATERNION_X) + ", " +
           semantic(VSOutputAttributes::QUATERNION_Y) + ", " +
           semantic(VSOutputAttributes::QUATERNION_Z) + ", " +
           semantic(VSOutputAttributes::QUATERNION_W) + ");\n";
    out += "}\n\n";

    out += "void EmitVtx(Vertex vtx, bool quats_opposite) {\n";
    out += "    vec4 vtx_pos = vec4(" + semantic(VSOutputAttributes::POSITION_X) + ", " +
           semantic(VSOutputAttributes::POSITION_Y) + ", " +
           semantic(VSOutputAttributes::POSITION_Z) + ", " +
           semantic(VSOutputAttributes::POSITION_W) + ");\n";
    out += "    gl_Position = vtx_pos;\n";
    out += "    gl_ClipDistance[0] = -vtx_pos.z;\n";     out += "    gl_ClipDistance[1] = dot(clip_coef, vtx_pos);\n\n";

    out += "    vec4 vtx_quat = GetVertexQuaternion(vtx);\n";
    out += "    normquat = mix(vtx_quat, -vtx_quat, bvec4(quats_opposite));\n\n";

    out += "    vec4 vtx_color = vec4(" + semantic(VSOutputAttributes::COLOR_R) + ", " +
           semantic(VSOutputAttributes::COLOR_G) + ", " + semantic(VSOutputAttributes::COLOR_B) +
           ", " + semantic(VSOutputAttributes::COLOR_A) + ");\n";
    out += "    primary_color = min(abs(vtx_color), vec4(1.0));\n\n";

    out += "    texcoord0 = vec2(" + semantic(VSOutputAttributes::TEXCOORD0_U) + ", " +
           semantic(VSOutputAttributes::TEXCOORD0_V) + ");\n";
    out += "    texcoord1 = vec2(" + semantic(VSOutputAttributes::TEXCOORD1_U) + ", " +
           semantic(VSOutputAttributes::TEXCOORD1_V) + ");\n\n";

    out += "    texcoord0_w = " + semantic(VSOutputAttributes::TEXCOORD0_W) + ";\n";
    out += "    view = vec3(" + semantic(VSOutputAttributes::VIEW_X) + ", " +
           semantic(VSOutputAttributes::VIEW_Y) + ", " + semantic(VSOutputAttributes::VIEW_Z) +
           ");\n\n";

    out += "    texcoord2 = vec2(" + semantic(VSOutputAttributes::TEXCOORD2_U) + ", " +
           semantic(VSOutputAttributes::TEXCOORD2_V) + ");\n\n";

    out += "    EmitVertex();\n";
    out += "}\n";

    out += R"(
bool AreQuaternionsOpposite(vec4 qa, vec4 qb) {
    return (dot(qa, qb) < 0.0);
}

void EmitPrim(Vertex vtx0, Vertex vtx1, Vertex vtx2) {
    EmitVtx(vtx0, false);
    EmitVtx(vtx1, AreQuaternionsOpposite(GetVertexQuaternion(vtx0), GetVertexQuaternion(vtx1)));
    EmitVtx(vtx2, AreQuaternionsOpposite(GetVertexQuaternion(vtx0), GetVertexQuaternion(vtx2)));
    EndPrimitive();
}
)";

    return out;
};

std::string GenerateFixedGeometryShader(const PicaFixedGSConfig& config, bool separable_shader) {
    std::string out = "#version 330 core\n";
    if (separable_shader) {
        out += "#extension GL_ARB_separate_shader_objects : enable\n\n";
    }

    out += R"(
layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

)";

    out += GetGSCommonSource(config.state, separable_shader);

    out += R"(
void main() {
    Vertex prim_buffer[3];
)";
    for (u32 vtx = 0; vtx < 3; ++vtx) {
        out += "    prim_buffer[" + std::to_string(vtx) + "].attributes = vec4[" +
               std::to_string(config.state.gs_output_attributes) + "](";
        for (u32 i = 0; i < config.state.vs_output_attributes; ++i) {
            out += std::string(i == 0 ? "" : ", ") + "vs_out_attr" + std::to_string(i) + "[" +
                   std::to_string(vtx) + "]";
        }
        out += ");\n";
    }
    out += "    EmitPrim(prim_buffer[0], prim_buffer[1], prim_buffer[2]);\n";
    out += "}\n";

    return out;
}

boost::optional<std::string> GenerateGeometryShader(const Pica::Shader::ShaderSetup& setup,
                                                    const PicaGSConfig& config,
                                                    bool separable_shader) {
    std::string out = "#version 330 core\n";
    if (separable_shader) {
        out += "#extension GL_ARB_separate_shader_objects : enable\n";
    }

    if (config.state.num_inputs % config.state.attributes_per_vertex != 0)
        return boost::none;

    switch (config.state.num_inputs / config.state.attributes_per_vertex) {
    case 1:
        out += "layout(points) in;\n";
        break;
    case 2:
        out += "layout(lines) in;\n";
        break;
    case 4:
        out += "layout(lines_adjacency) in;\n";
        break;
    case 3:
        out += "layout(triangles) in;\n";
        break;
    case 6:
        out += "layout(triangles_adjacency) in;\n";
        break;
    default:
        return boost::none;
    }
    out += "layout(triangle_strip, max_vertices = 30) out;\n\n";

    out += GetGSCommonSource(config.state, separable_shader);

    auto get_input_reg = [&](u32 reg) -> std::string {
        ASSERT(reg < 16);
        u32 attr = config.state.input_map[reg];
        if (attr < config.state.num_inputs) {
            return "vs_out_attr" + std::to_string(attr % config.state.attributes_per_vertex) + "[" +
                   std::to_string(attr / config.state.attributes_per_vertex) + "]";
        }
        return "vec4(0.0, 0.0, 0.0, 1.0)";
    };

    auto get_output_reg = [&](u32 reg) -> std::string {
        ASSERT(reg < 16);
        if (config.state.output_map[reg] < config.state.num_outputs) {
            return "output_buffer.attributes[" + std::to_string(config.state.output_map[reg]) + "]";
        }
        return "";
    };

    auto program_source_opt = Pica::Shader::Decompiler::DecompileProgram(
        setup.program_code, setup.swizzle_data, config.state.main_offset, get_input_reg,
        get_output_reg, config.state.sanitize_mul, true);

    if (!program_source_opt)
        return boost::none;

    std::string& program_source = program_source_opt.get();

    out += R"(
Vertex output_buffer;
Vertex prim_buffer[3];
uint vertex_id = 0u;
bool prim_emit = false;
bool winding = false;

void setemit(uint vertex_id_, bool prim_emit_, bool winding_) {
    vertex_id = vertex_id_;
    prim_emit = prim_emit_;
    winding = winding_;
}

void emit() {
    prim_buffer[vertex_id] = output_buffer;

    if (prim_emit) {
        if (winding) {
            EmitPrim(prim_buffer[1], prim_buffer[0], prim_buffer[2]);
            winding = false;
        } else {
            EmitPrim(prim_buffer[0], prim_buffer[1], prim_buffer[2]);
        }
    }
}

void main() {
)";
    for (u32 i = 0; i < config.state.num_outputs; ++i) {
        out +=
            "    output_buffer.attributes[" + std::to_string(i) + "] = vec4(0.0, 0.0, 0.0, 1.0);\n";
    }

        out += "\n    exec_shader();\n\n";

    out += "}\n\n";

    out += program_source;

    return out;
}

} 