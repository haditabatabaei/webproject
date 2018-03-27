
package org.elasticsearch.search.aggregations.pipeline.bucketsort;


import org.apache.lucene.util.PriorityQueue;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.InternalAggregation.ReduceContext;
import org.elasticsearch.search.aggregations.InternalMultiBucketAggregation;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.pipeline.BucketHelpers;
import org.elasticsearch.search.aggregations.pipeline.BucketHelpers.GapPolicy;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregator;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BucketSortPipelineAggregator extends PipelineAggregator {

    private final List<FieldSortBuilder> sorts;
    private final int from;
    private final Integer size;
    private final GapPolicy gapPolicy;

    public BucketSortPipelineAggregator(String name, List<FieldSortBuilder> sorts, int from, Integer size, GapPolicy gapPolicy,
                                        Map<String, Object> metadata) {
        super(name, sorts.stream().map(s -> s.getFieldName()).toArray(String[]::new), metadata);
        this.sorts = sorts;
        this.from = from;
        this.size = size;
        this.gapPolicy = gapPolicy;
    }

    
    public BucketSortPipelineAggregator(StreamInput in) throws IOException {
        super(in);
        sorts = in.readList(FieldSortBuilder::new);
        from = in.readVInt();
        size = in.readOptionalVInt();
        gapPolicy = GapPolicy.readFrom(in);
    }

    @Override
    protected void doWriteTo(StreamOutput out) throws IOException {
        out.writeList(sorts);
        out.writeVInt(from);
        out.writeOptionalVInt(size);
        gapPolicy.writeTo(out);
    }

    @Override
    public String getWriteableName() {
        return BucketSortPipelineAggregationBuilder.NAME;
    }

    @Override
    public InternalAggregation reduce(InternalAggregation aggregation, ReduceContext reduceContext) {
        InternalMultiBucketAggregation<InternalMultiBucketAggregation, InternalMultiBucketAggregation.InternalBucket> originalAgg =
                (InternalMultiBucketAggregation<InternalMultiBucketAggregation, InternalMultiBucketAggregation.InternalBucket>) aggregation;
        List<? extends InternalMultiBucketAggregation.InternalBucket> buckets = originalAgg.getBuckets();
        int bucketsCount = buckets.size();
        int currentSize = size == null ? bucketsCount : size;

        if (from >= bucketsCount) {
            return originalAgg.create(Collections.emptyList());
        }

                if (sorts.size() == 0) {
            return originalAgg.create(new ArrayList<>(buckets.subList(from, Math.min(from + currentSize, bucketsCount))));
        }

        int queueSize = Math.min(from + currentSize, bucketsCount);
        PriorityQueue<ComparableBucket> ordered = new TopNPriorityQueue(queueSize);
        for (InternalMultiBucketAggregation.InternalBucket bucket : buckets) {
            ComparableBucket comparableBucket = new ComparableBucket(originalAgg, bucket);
            if (comparableBucket.skip() == false) {
                ordered.insertWithOverflow(new ComparableBucket(originalAgg, bucket));
            }
        }

        int resultSize = Math.max(ordered.size() - from, 0);

                        LinkedList<InternalMultiBucketAggregation.InternalBucket> newBuckets = new LinkedList<>();
        for (int i = 0; i < resultSize; ++i) {
            newBuckets.addFirst(ordered.pop().internalBucket);
        }
        return originalAgg.create(newBuckets);
    }

    private class ComparableBucket implements Comparable<ComparableBucket> {

        private final MultiBucketsAggregation parentAgg;
        private final InternalMultiBucketAggregation.InternalBucket internalBucket;
        private final Map<FieldSortBuilder, Comparable<Object>> sortValues;

        private ComparableBucket(MultiBucketsAggregation parentAgg, InternalMultiBucketAggregation.InternalBucket internalBucket) {
            this.parentAgg = parentAgg;
            this.internalBucket = internalBucket;
            this.sortValues = resolveAndCacheSortValues();
        }

        private Map<FieldSortBuilder, Comparable<Object>> resolveAndCacheSortValues() {
            Map<FieldSortBuilder, Comparable<Object>> resolved = new HashMap<>();
            for (FieldSortBuilder sort : sorts) {
                String sortField = sort.getFieldName();
                if ("_key".equals(sortField)) {
                    resolved.put(sort, (Comparable<Object>) internalBucket.getKey());
                } else {
                    Double bucketValue = BucketHelpers.resolveBucketValue(parentAgg, internalBucket, sortField, gapPolicy);
                    if (GapPolicy.SKIP == gapPolicy && Double.isNaN(bucketValue)) {
                        continue;
                    }
                    resolved.put(sort, (Comparable<Object>) (Object) bucketValue);
                }
            }
            return resolved;
        }

        
        private boolean skip() {
            return sortValues.isEmpty();
        }

        @Override
        public int compareTo(ComparableBucket that) {
            int compareResult = 0;
            for (FieldSortBuilder sort : sorts) {
                Comparable<Object> thisValue = this.sortValues.get(sort);
                Comparable<Object> thatValue = that.sortValues.get(sort);
                if (thisValue == null && thatValue == null) {
                    continue;
                } else if (thisValue == null) {
                    return -1;
                } else if (thatValue == null) {
                    return 1;
                } else {
                    compareResult = sort.order() == SortOrder.DESC ? thisValue.compareTo(thatValue) : -thisValue.compareTo(thatValue);
                }
                if (compareResult != 0) {
                    break;
                }
            }
            return compareResult;
        }
    }


    private static class TopNPriorityQueue extends PriorityQueue<ComparableBucket> {

        private TopNPriorityQueue(int n) {
            super(n, false);
        }

        @Override
        protected boolean lessThan(ComparableBucket a, ComparableBucket b) {
            return a.compareTo(b) < 0;
        }
    }
}
