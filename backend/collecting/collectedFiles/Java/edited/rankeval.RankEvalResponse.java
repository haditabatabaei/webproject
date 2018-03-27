

package org.elasticsearch.index.rankeval;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.collect.Tuple;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.ConstructingObjectParser;
import org.elasticsearch.common.xcontent.ToXContentObject;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentParserUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class RankEvalResponse extends ActionResponse implements ToXContentObject {

    
    private double evaluationResult;
    
    private Map<String, EvalQueryQuality> details;
    
    private Map<String, Exception> failures;

    public RankEvalResponse(double qualityLevel, Map<String, EvalQueryQuality> partialResults,
            Map<String, Exception> failures) {
        this.evaluationResult = qualityLevel;
        this.details =  new HashMap<>(partialResults);
        this.failures = new HashMap<>(failures);
    }

    RankEvalResponse() {
            }

    public double getEvaluationResult() {
        return evaluationResult;
    }

    public Map<String, EvalQueryQuality> getPartialResults() {
        return Collections.unmodifiableMap(details);
    }

    public Map<String, Exception> getFailures() {
        return Collections.unmodifiableMap(failures);
    }

    @Override
    public String toString() {
        return Strings.toString(this);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeDouble(evaluationResult);
        out.writeVInt(details.size());
        for (String queryId : details.keySet()) {
            out.writeString(queryId);
            details.get(queryId).writeTo(out);
        }
        out.writeVInt(failures.size());
        for (String queryId : failures.keySet()) {
            out.writeString(queryId);
            out.writeException(failures.get(queryId));
        }
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        this.evaluationResult = in.readDouble();
        int partialResultSize = in.readVInt();
        this.details = new HashMap<>(partialResultSize);
        for (int i = 0; i < partialResultSize; i++) {
            String queryId = in.readString();
            EvalQueryQuality partial = new EvalQueryQuality(in);
            this.details.put(queryId, partial);
        }
        int failuresSize = in.readVInt();
        this.failures = new HashMap<>(failuresSize);
        for (int i = 0; i < failuresSize; i++) {
            String queryId = in.readString();
            this.failures.put(queryId, in.readException());
        }
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject();
        builder.field("quality_level", evaluationResult);
        builder.startObject("details");
        for (String key : details.keySet()) {
            details.get(key).toXContent(builder, params);
        }
        builder.endObject();
        builder.startObject("failures");
        for (String key : failures.keySet()) {
            builder.startObject(key);
            ElasticsearchException.generateFailureXContent(builder, params, failures.get(key), true);
            builder.endObject();
        }
        builder.endObject();
        builder.endObject();
        return builder;
    }

    private static final ParseField QUALITY_LEVEL_FIELD = new ParseField("quality_level");
    private static final ParseField DETAILS_FIELD = new ParseField("details");
    private static final ParseField FAILURES_FIELD = new ParseField("failures");
    @SuppressWarnings("unchecked")
    private static final ConstructingObjectParser<RankEvalResponse, Void> PARSER = new ConstructingObjectParser<>("rank_eval_response",
            true,
            a -> new RankEvalResponse((Double) a[0],
                    ((List<EvalQueryQuality>) a[1]).stream().collect(Collectors.toMap(EvalQueryQuality::getId, Function.identity())),
                    ((List<Tuple<String, Exception>>) a[2]).stream().collect(Collectors.toMap(Tuple::v1, Tuple::v2))));
    static {
        PARSER.declareDouble(ConstructingObjectParser.constructorArg(), QUALITY_LEVEL_FIELD);
        PARSER.declareNamedObjects(ConstructingObjectParser.optionalConstructorArg(), (p, c, n) -> EvalQueryQuality.fromXContent(p, n),
                DETAILS_FIELD);
        PARSER.declareNamedObjects(ConstructingObjectParser.optionalConstructorArg(), (p, c, n) -> {
            XContentParserUtils.ensureExpectedToken(XContentParser.Token.START_OBJECT, p.nextToken(), p::getTokenLocation);
            XContentParserUtils.ensureExpectedToken(XContentParser.Token.FIELD_NAME, p.nextToken(), p::getTokenLocation);
            Tuple<String, ElasticsearchException> tuple = new Tuple<>(n, ElasticsearchException.failureFromXContent(p));
            XContentParserUtils.ensureExpectedToken(XContentParser.Token.END_OBJECT, p.nextToken(), p::getTokenLocation);
            return tuple;
        }, FAILURES_FIELD);

    }

    public static RankEvalResponse fromXContent(XContentParser parser) throws IOException {
        return PARSER.apply(parser, null);
    }
}
