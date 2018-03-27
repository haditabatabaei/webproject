

package org.elasticsearch.search.aggregations.bucket.adjacency;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Weight;
import org.elasticsearch.search.aggregations.Aggregator;
import org.elasticsearch.search.aggregations.AggregatorFactories;
import org.elasticsearch.search.aggregations.AggregatorFactory;
import org.elasticsearch.search.aggregations.bucket.adjacency.AdjacencyMatrixAggregator.KeyedFilter;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregator;
import org.elasticsearch.search.internal.SearchContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AdjacencyMatrixAggregatorFactory extends AggregatorFactory<AdjacencyMatrixAggregatorFactory> {

    private final String[] keys;
    private final Weight[] weights;
    private final String separator;

    public AdjacencyMatrixAggregatorFactory(String name, List<KeyedFilter> filters, String separator, 
            SearchContext context, AggregatorFactory<?> parent, AggregatorFactories.Builder subFactories, 
            Map<String, Object> metaData) throws IOException {
        super(name, context, parent, subFactories, metaData);
        IndexSearcher contextSearcher = context.searcher();
        this.separator = separator;
        weights = new Weight[filters.size()];
        keys = new String[filters.size()];
        for (int i = 0; i < filters.size(); ++i) {
            KeyedFilter keyedFilter = filters.get(i);
            this.keys[i] = keyedFilter.key();
            Query filter = keyedFilter.filter().toFilter(context.getQueryShardContext());
            this.weights[i] = contextSearcher.createNormalizedWeight(filter, false);
        }
    }

    @Override
    public Aggregator createInternal(Aggregator parent, boolean collectsFromSingleBucket, List<PipelineAggregator> pipelineAggregators,
            Map<String, Object> metaData) throws IOException {
        return new AdjacencyMatrixAggregator(name, factories, separator, keys, weights, context, parent,
                pipelineAggregators, metaData);
    }

}
