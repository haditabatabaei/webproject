

package org.elasticsearch.action.ingest;

import org.elasticsearch.Version;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.elasticsearch.common.xcontent.ToXContent.Params;
import org.elasticsearch.common.xcontent.ToXContentFragment;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.ingest.IngestDocument;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

final class WriteableIngestDocument implements Writeable, ToXContentFragment {

    private final IngestDocument ingestDocument;

    WriteableIngestDocument(IngestDocument ingestDocument) {
        assert ingestDocument != null;
        this.ingestDocument = ingestDocument;
    }

    WriteableIngestDocument(StreamInput in) throws IOException {
        Map<String, Object> sourceAndMetadata = in.readMap();
        Map<String, Object> ingestMetadata = in.readMap();
        if (in.getVersion().before(Version.V_6_0_0_beta1)) {
            ingestMetadata.computeIfPresent("timestamp", (k, o) -> {
                Date date = (Date) o;
                return date.toInstant().atZone(ZoneId.systemDefault());
            });
        }
        this.ingestDocument = new IngestDocument(sourceAndMetadata, ingestMetadata);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeMap(ingestDocument.getSourceAndMetadata());
        out.writeMap(ingestDocument.getIngestMetadata());
    }

    IngestDocument getIngestDocument() {
        return ingestDocument;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject("doc");
        Map<IngestDocument.MetaData, Object> metadataMap = ingestDocument.extractMetadata();
        for (Map.Entry<IngestDocument.MetaData, Object> metadata : metadataMap.entrySet()) {
            if (metadata.getValue() != null) {
                builder.field(metadata.getKey().getFieldName(), metadata.getValue().toString());
            }
        }
        builder.field("_source", ingestDocument.getSourceAndMetadata());
        builder.field("_ingest", ingestDocument.getIngestMetadata());
        builder.endObject();
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WriteableIngestDocument that = (WriteableIngestDocument) o;
        return Objects.equals(ingestDocument, that.ingestDocument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingestDocument);
    }

    @Override
    public String toString() {
        return ingestDocument.toString();
    }
}
