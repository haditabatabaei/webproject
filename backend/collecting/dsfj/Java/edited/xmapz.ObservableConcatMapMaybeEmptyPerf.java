

package io.reactivex.xmapz;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import io.reactivex.*;
import io.reactivex.functions.Function;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class ObservableConcatMapMaybeEmptyPerf {
    @Param({ "1", "10", "100", "1000", "10000", "100000", "1000000" })
    public int count;

    Observable<Integer> concatMapToObservableEmpty;

    Observable<Integer> observableDedicated;

    Observable<Integer> observablePlain;

    @Setup
    public void setup() {
        Integer[] sourceArray = new Integer[count];
        Arrays.fill(sourceArray, 777);

        Observable<Integer> source = Observable.fromArray(sourceArray);

        observablePlain = source.concatMap(new Function<Integer, Observable<? extends Integer>>() {
            @Override
            public Observable<? extends Integer> apply(Integer v)
                    throws Exception {
                return Observable.empty();
            }
        });

        concatMapToObservableEmpty = source.concatMap(new Function<Integer, Observable<? extends Integer>>() {
            @Override
            public Observable<? extends Integer> apply(Integer v)
                    throws Exception {
                return Maybe.<Integer>empty().toObservable();
            }
        });

        observableDedicated = source.concatMapMaybe(new Function<Integer, Maybe<? extends Integer>>() {
            @Override
            public Maybe<? extends Integer> apply(Integer v)
                    throws Exception {
                return Maybe.empty();
            }
        });
    }

    @Benchmark
    public Object observablePlain(Blackhole bh) {
        return observablePlain.subscribeWith(new PerfConsumer(bh));
    }

    @Benchmark
    public Object observableConvert(Blackhole bh) {
        return concatMapToObservableEmpty.subscribeWith(new PerfConsumer(bh));
    }

    @Benchmark
    public Object observableDedicated(Blackhole bh) {
        return observableDedicated.subscribeWith(new PerfConsumer(bh));
    }
}
