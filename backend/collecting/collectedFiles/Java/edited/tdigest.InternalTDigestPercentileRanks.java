
package org.elasticsearch.search.aggregations.metrics.percentiles.tdigest;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.search.DocValueFormat;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentileRanks;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregator;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InternalTDigestPercentileRanks extends AbstractInternalTDigestPercentiles implements PercentileRanks {
    public static final String NAME = "tdigest_percentile_ranks";

    public InternalTDigestPercentileRanks(String name, double[] cdfValues, TDigestState state, boolean keyed, DocValueFormat formatter,
            List<PipelineAggregator> pipelineAggregators, Map<String, Object> metaData) {
        super(name, cdfValues, state, keyed, formatter, pipelineAggregators, metaData);
    }

    
    public InternalTDigestPercentileRanks(StreamInput in) throws IOException {
        super(in);
    }

    @Override
    public String getWriteableName() {
        return NAME;
    }

    @Override
    public Iterator<Percentile> iterator() {
        return new Iter(keys, state);
    }

    @Override
    public double percent(double value) {
        return percentileRank(state, value);
    }

    @Override
    public String percentAsString(double value) {
        return valueAsString(String.valueOf(value));
    }

    @Override
    public double value(double key) {
        return percent(key);
    }

    @Override
    protected AbstractInternalTDigestPercentiles createReduced(String name, double[] keys, TDigestState merged, boolean keyed,
            List<PipelineAggregator> pipelineAggregators, Map<String, Object> metaData) {
        return new InternalTDigestPercentileRanks(name, keys, merged, keyed, format, pipelineAggregators, metaData);
    }

    static double percentileRank(TDigestState state, double value) {
        double percentileRank = state.cdf(value);
        if (percentileRank < 0) {
            percentileRank = 0;
        }
        else if (percentileRank > 1) {
            percentileRank = 1;
        }
        return percentileRank * 100;
    }

    public static class Iter implements Iterator<Percentile> {

        private final double[] values;
        private final TDigestState state;
        private int i;

        public Iter(double[] values, TDigestState state) {
            this.values = values;
            this.state = state;
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < values.length;
        }

        @Override
        public Percentile next() {
            final Percentile next = new Percentile(percentileRank(state, values[i]), values[i]);
            ++i;
            return next;
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
