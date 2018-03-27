

package org.elasticsearch.search.aggregations.bucket.nested;

import org.elasticsearch.index.mapper.ObjectMapper;
import org.elasticsearch.search.aggregations.Aggregator;
import org.elasticsearch.search.aggregations.AggregatorFactories;
import org.elasticsearch.search.aggregations.AggregatorFactory;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.NonCollectingAggregator;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregator;
import org.elasticsearch.search.internal.SearchContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReverseNestedAggregatorFactory extends AggregatorFactory<ReverseNestedAggregatorFactory> {

    private final boolean unmapped;
    private final ObjectMapper parentObjectMapper;

    public ReverseNestedAggregatorFactory(String name, boolean unmapped, ObjectMapper parentObjectMapper,
                                          SearchContext context, AggregatorFactory<?> parent,
                                          AggregatorFactories.Builder subFactories,
                                          Map<String, Object> metaData) throws IOException {
        super(name, context, parent, subFactories, metaData);
        this.unmapped = unmapped;
        this.parentObjectMapper = parentObjectMapper;
    }

    @Override
    public Aggregator createInternal(Aggregator parent, boolean collectsFromSingleBucket, List<PipelineAggregator> pipelineAggregators,
            Map<String, Object> metaData) throws IOException {
        if (unmapped) {
            return new Unmapped(name, context, parent, pipelineAggregators, metaData);
        } else {
            return new ReverseNestedAggregator(name, factories, parentObjectMapper, context, parent, pipelineAggregators, metaData);
        }
    }

    private static final class Unmapped extends NonCollectingAggregator {

        Unmapped(String name, SearchContext context, Aggregator parent, List<PipelineAggregator> pipelineAggregators,
                Map<String, Object> metaData) throws IOException {
            super(name, context, parent, pipelineAggregators, metaData);
        }

        @Override
        public InternalAggregation buildEmptyAggregation() {
            return new InternalReverseNested(name, 0, buildEmptySubAggregations(), pipelineAggregators(), metaData());
        }
    }
}
