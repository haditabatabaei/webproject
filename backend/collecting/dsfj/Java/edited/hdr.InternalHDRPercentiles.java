
package org.elasticsearch.search.aggregations.metrics.percentiles.hdr;

import org.HdrHistogram.DoubleHistogram;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.search.DocValueFormat;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregator;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InternalHDRPercentiles extends AbstractInternalHDRPercentiles implements Percentiles {
    public static final String NAME = "hdr_percentiles";

    public InternalHDRPercentiles(String name, double[] percents, DoubleHistogram state, boolean keyed, DocValueFormat formatter,
            List<PipelineAggregator> pipelineAggregators, Map<String, Object> metaData) {
        super(name, percents, state, keyed, formatter, pipelineAggregators, metaData);
    }

    
    public InternalHDRPercentiles(StreamInput in) throws IOException {
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
    public double percentile(double percent) {
        if (state.getTotalCount() == 0) {
            return Double.NaN;
        }
        return state.getValueAtPercentile(percent);
    }

    @Override
    public String percentileAsString(double percent) {
        return valueAsString(String.valueOf(percent));
    }

    @Override
    public double value(double key) {
        return percentile(key);
    }

    @Override
    protected AbstractInternalHDRPercentiles createReduced(String name, double[] keys, DoubleHistogram merged, boolean keyed,
            List<PipelineAggregator> pipelineAggregators, Map<String, Object> metaData) {
        return new InternalHDRPercentiles(name, keys, merged, keyed, format, pipelineAggregators, metaData);
    }

    public static class Iter implements Iterator<Percentile> {

        private final double[] percents;
        private final DoubleHistogram state;
        private int i;

        public Iter(double[] percents, DoubleHistogram state) {
            this.percents = percents;
            this.state = state;
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < percents.length;
        }

        @Override
        public Percentile next() {
            double percent = percents[i];
            double value = (state.getTotalCount() == 0) ? Double.NaN : state.getValueAtPercentile(percent);
            final Percentile next = new Percentile(percent, value);
            ++i;
            return next;
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }
}