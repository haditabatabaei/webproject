

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

class NestedAggregatorFactory extends AggregatorFactory<NestedAggregatorFactory> {

    private final ObjectMapper parentObjectMapper;
    private final ObjectMapper childObjectMapper;

    NestedAggregatorFactory(String name, ObjectMapper parentObjectMapper, ObjectMapper childObjectMapper,
            SearchContext context, AggregatorFactory<?> parent, AggregatorFactories.Builder subFactories,
                                   Map<String, Object> metaData) throws IOException {
        super(name, context, parent, subFactories, metaData);
        this.parentObjectMapper = parentObjectMapper;
        this.childObjectMapper = childObjectMapper;
    }

    @Override
    public Aggregator createInternal(Aggregator parent, boolean collectsFromSingleBucket, List<PipelineAggregator> pipelineAggregators,
            Map<String, Object> metaData) throws IOException {
        if (childObjectMapper == null) {
            return new Unmapped(name, context, parent, pipelineAggregators, metaData);
        }
        return new NestedAggregator(name, factories, parentObjectMapper, childObjectMapper, context, parent,
            pipelineAggregators, metaData, collectsFromSingleBucket);
    }

    private static final class Unmapped extends NonCollectingAggregator {

        Unmapped(String name, SearchContext context, Aggregator parent, List<PipelineAggregator> pipelineAggregators,
                Map<String, Object> metaData) throws IOException {
            super(name, context, parent, pipelineAggregators, metaData);
        }

        @Override
        public InternalAggregation buildEmptyAggregation() {
            return new InternalNested(name, 0, buildEmptySubAggregations(), pipelineAggregators(), metaData());
        }
    }

}
