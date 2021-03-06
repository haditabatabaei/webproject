


#include "wx/wxprec.h"

#ifdef __BORLANDC__
    #pragma hdrstop
#endif

#if wxUSE_UXTHEME

#ifndef WX_PRECOMP
    #include "wx/app.h"
    #include "wx/toplevel.h"
    #include "wx/string.h"
    #include "wx/log.h"
    #include "wx/module.h"
#endif 
#include "wx/msw/uxtheme.h"


class wxUxThemeModule : public wxModule
{
public:
    virtual bool OnInit() { return true; }
    virtual void OnExit()
    {
        if ( wxUxThemeEngine::ms_themeEngine )
        {
                                                                        wxUxThemeEngine *themeEngine = wxUxThemeEngine::ms_themeEngine;
            wxUxThemeEngine::ms_themeEngine = NULL;
            wxUxThemeEngine::ms_isThemeEngineAvailable = -1;

            delete themeEngine;
        }
    }


    wxDECLARE_DYNAMIC_CLASS(wxUxThemeModule);
};

wxIMPLEMENT_DYNAMIC_CLASS(wxUxThemeModule, wxModule);


wxUxThemeEngine *wxUxThemeEngine::ms_themeEngine = NULL;
int wxUxThemeEngine::ms_isThemeEngineAvailable = -1;        
wxUxThemeEngine* wxUxThemeEngine::Get()
{
            if ( ms_isThemeEngineAvailable == -1 )
    {
                ms_themeEngine = new wxUxThemeEngine;
        if ( !ms_themeEngine->Initialize() )
        {
                                    delete ms_themeEngine;
            ms_themeEngine = NULL;

            ms_isThemeEngineAvailable = false;
        }
        else         {
            ms_isThemeEngineAvailable = true;
        }
    }

    return ms_themeEngine;
}

bool wxUxThemeEngine::Initialize()
{
    if ( wxApp::GetComCtl32Version() < 600 )
    {
                        return false;
    }

        wxLogNull noLog;

    if ( !m_dllUxTheme.Load(wxT("uxtheme.dll")) )
        return false;

#define RESOLVE_OPTIONAL_UXTHEME_FUNCTION(type, funcname)                     \
    funcname = (type)m_dllUxTheme.GetSymbol(wxT(#funcname))

#define RESOLVE_UXTHEME_FUNCTION(type, funcname)                              \
    RESOLVE_OPTIONAL_UXTHEME_FUNCTION(type, funcname);						  \
    if ( !funcname )                                                          \
        return false

    RESOLVE_UXTHEME_FUNCTION(PFNWXUOPENTHEMEDATA, OpenThemeData);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUCLOSETHEMEDATA, CloseThemeData);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUDRAWTHEMEBACKGROUND, DrawThemeBackground);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUDRAWTHEMETEXT, DrawThemeText);
            RESOLVE_OPTIONAL_UXTHEME_FUNCTION(PFNWXUDRAWTHEMETEXTEX, DrawThemeTextEx);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEBACKGROUNDCONTENTRECT, GetThemeBackgroundContentRect);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEBACKGROUNDEXTENT, GetThemeBackgroundExtent);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEPARTSIZE, GetThemePartSize);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMETEXTEXTENT, GetThemeTextExtent);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMETEXTMETRICS, GetThemeTextMetrics);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEBACKGROUNDREGION, GetThemeBackgroundRegion);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUHITTESTTHEMEBACKGROUND, HitTestThemeBackground);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUDRAWTHEMEEDGE, DrawThemeEdge);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUDRAWTHEMEICON, DrawThemeIcon);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUISTHEMEPARTDEFINED, IsThemePartDefined);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUISTHEMEBACKGROUNDPARTIALLYTRANSPARENT, IsThemeBackgroundPartiallyTransparent);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMECOLOR, GetThemeColor);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEMETRIC, GetThemeMetric);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESTRING, GetThemeString);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEBOOL, GetThemeBool);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEINT, GetThemeInt);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEENUMVALUE, GetThemeEnumValue);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEPOSITION, GetThemePosition);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEFONT, GetThemeFont);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMERECT, GetThemeRect);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEMARGINS, GetThemeMargins);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEINTLIST, GetThemeIntList);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEPROPERTYORIGIN, GetThemePropertyOrigin);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUSETWINDOWTHEME, SetWindowTheme);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEFILENAME, GetThemeFilename);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSCOLOR, GetThemeSysColor);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSCOLORBRUSH, GetThemeSysColorBrush);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSBOOL, GetThemeSysBool);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSSIZE, GetThemeSysSize);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSFONT, GetThemeSysFont);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSSTRING, GetThemeSysString);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMESYSINT, GetThemeSysInt);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUISTHEMEACTIVE, IsThemeActive);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUISAPPTHEMED, IsAppThemed);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETWINDOWTHEME, GetWindowTheme);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUENABLETHEMEDIALOGTEXTURE, EnableThemeDialogTexture);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUISTHEMEDIALOGTEXTUREENABLED, IsThemeDialogTextureEnabled);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEAPPPROPERTIES, GetThemeAppProperties);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUSETTHEMEAPPPROPERTIES, SetThemeAppProperties);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETCURRENTTHEMENAME, GetCurrentThemeName);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUGETTHEMEDOCUMENTATIONPROPERTY, GetThemeDocumentationProperty);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUDRAWTHEMEPARENTBACKGROUND, DrawThemeParentBackground);
    RESOLVE_UXTHEME_FUNCTION(PFNWXUENABLETHEMING, EnableTheming);

#undef RESOLVE_UXTHEME_FUNCTION

    return true;
}

#endif 