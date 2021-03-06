

package io.reactivex;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import io.reactivex.functions.Action;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class FlowableFlatMapCompletableAsyncPerf implements Action {

    @Param({"1", "10", "100", "1000", "10000", "100000", "1000000"})
    int items;

    @Param({"1", "8", "32", "128", "256"})
    int maxConcurrency;

    @Param({"1", "10", "100", "1000"})
    int work;

    Completable flatMapCompletable;

    Flowable<Object> flatMap;

    @Override
    public void run() throws Exception {
        Blackhole.consumeCPU(work);
    }

    @Setup
    public void setup() {
        Integer[] array = new Integer[items];
        Arrays.fill(array, 777);

        flatMapCompletable = Flowable.fromArray(array)
                .flatMapCompletable(Functions.justFunction(Completable.fromAction(this).subscribeOn(Schedulers.computation())), false, maxConcurrency);

        flatMap = Flowable.fromArray(array)
                .flatMap(Functions.justFunction(Completable.fromAction(this).subscribeOn(Schedulers.computation()).toFlowable()), false, maxConcurrency);
    }

    public Object flatMap(Blackhole bh) {
        return flatMap.subscribeWith(new PerfAsyncConsumer(bh)).await(items);
    }

    @Benchmark
    public Object flatMapCompletable(Blackhole bh) {
        return flatMapCompletable.subscribeWith(new PerfAsyncConsumer(bh)).await(items);
    }
}
