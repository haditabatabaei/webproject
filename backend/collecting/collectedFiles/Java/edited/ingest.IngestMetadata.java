

package org.elasticsearch.ingest;

import org.elasticsearch.cluster.Diff;
import org.elasticsearch.cluster.DiffableUtils;
import org.elasticsearch.cluster.NamedDiff;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.ObjectParser;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class IngestMetadata implements MetaData.Custom {

    public static final String TYPE = "ingest";
    private static final ParseField PIPELINES_FIELD = new ParseField("pipeline");
    private static final ObjectParser<List<PipelineConfiguration>, Void> INGEST_METADATA_PARSER = new ObjectParser<>(
            "ingest_metadata", ArrayList::new);

    static {
        INGEST_METADATA_PARSER.declareObjectArray(List::addAll , PipelineConfiguration.getParser(), PIPELINES_FIELD);
    }

            private final Map<String, PipelineConfiguration> pipelines;

    private IngestMetadata() {
        this.pipelines = Collections.emptyMap();
    }

    public IngestMetadata(Map<String, PipelineConfiguration> pipelines) {
        this.pipelines = Collections.unmodifiableMap(pipelines);
    }

    @Override
    public String getWriteableName() {
        return TYPE;
    }

    public Map<String, PipelineConfiguration> getPipelines() {
        return pipelines;
    }

    public IngestMetadata(StreamInput in) throws IOException {
        int size = in.readVInt();
        Map<String, PipelineConfiguration> pipelines = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            PipelineConfiguration pipeline = PipelineConfiguration.readFrom(in);
            pipelines.put(pipeline.getId(), pipeline);
        }
        this.pipelines = Collections.unmodifiableMap(pipelines);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeVInt(pipelines.size());
        for (PipelineConfiguration pipeline : pipelines.values()) {
            pipeline.writeTo(out);
        }
    }

    public static IngestMetadata fromXContent(XContentParser parser) throws IOException {
        Map<String, PipelineConfiguration> pipelines = new HashMap<>();
        List<PipelineConfiguration> configs = INGEST_METADATA_PARSER.parse(parser, null);
        for (PipelineConfiguration pipeline : configs) {
            pipelines.put(pipeline.getId(), pipeline);
        }
        return new IngestMetadata(pipelines);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startArray(PIPELINES_FIELD.getPreferredName());
        for (PipelineConfiguration pipeline : pipelines.values()) {
            pipeline.toXContent(builder, params);
        }
        builder.endArray();
        return builder;
    }

    @Override
    public EnumSet<MetaData.XContentContext> context() {
        return MetaData.ALL_CONTEXTS;
    }

    @Override
    public Diff<MetaData.Custom> diff(MetaData.Custom before) {
        return new IngestMetadataDiff((IngestMetadata) before, this);
    }

    public static NamedDiff<MetaData.Custom> readDiffFrom(StreamInput in) throws IOException {
        return new IngestMetadataDiff(in);
    }

    static class IngestMetadataDiff implements NamedDiff<MetaData.Custom> {

        final Diff<Map<String, PipelineConfiguration>> pipelines;

        IngestMetadataDiff(IngestMetadata before, IngestMetadata after) {
            this.pipelines = DiffableUtils.diff(before.pipelines, after.pipelines, DiffableUtils.getStringKeySerializer());
        }

        IngestMetadataDiff(StreamInput in) throws IOException {
            pipelines = DiffableUtils.readJdkMapDiff(in, DiffableUtils.getStringKeySerializer(), PipelineConfiguration::readFrom,
                PipelineConfiguration::readDiffFrom);
        }

        @Override
        public MetaData.Custom apply(MetaData.Custom part) {
            return new IngestMetadata(pipelines.apply(((IngestMetadata) part).pipelines));
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            pipelines.writeTo(out);
        }

        @Override
        public String getWriteableName() {
            return TYPE;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngestMetadata that = (IngestMetadata) o;

        return pipelines.equals(that.pipelines);

    }

    @Override
    public int hashCode() {
        return pipelines.hashCode();
    }
}
