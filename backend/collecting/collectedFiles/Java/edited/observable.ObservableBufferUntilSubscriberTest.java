

package io.reactivex.internal.operators.observable;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import org.junit.*;

import io.reactivex.Observable;
import io.reactivex.functions.*;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ObservableBufferUntilSubscriberTest {

    @Test
    public void testIssue1677() throws InterruptedException {
        final AtomicLong counter = new AtomicLong();
        final Integer[] numbers = new Integer[5000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }
        final int NITERS = 250;
        final CountDownLatch latch = new CountDownLatch(NITERS);
        for (int iters = 0; iters < NITERS; iters++) {
            final CountDownLatch innerLatch = new CountDownLatch(1);
            final PublishSubject<Void> s = PublishSubject.create();
            final AtomicBoolean completed = new AtomicBoolean();
            Observable.fromArray(numbers)
                    .takeUntil(s)
                    .window(50)
                    .flatMap(new Function<Observable<Integer>, Observable<Object>>() {
                        @Override
                        public Observable<Object> apply(Observable<Integer> integerObservable) {
                                return integerObservable
                                        .subscribeOn(Schedulers.computation())
                                        .map(new Function<Integer, Object>() {
                                            @Override
                                            public Object apply(Integer integer) {
                                                    if (integer >= 5 && completed.compareAndSet(false, true)) {
                                                        s.onComplete();
                                                    }
                                                                                                        Math.pow(Math.random(), Math.random());
                                                    return integer * 2;
                                            }
                                        });
                        }
                    })
                    .toList()
                    .doOnSuccess(new Consumer<List<Object>>() {
                        @Override
                        public void accept(List<Object> integers) {
                                counter.incrementAndGet();
                                latch.countDown();
                                innerLatch.countDown();
                        }
                    })
                    .subscribe();
            if (!innerLatch.await(30, TimeUnit.SECONDS)) {
                Assert.fail("Failed inner latch wait, iteration " + iters);
            }
        }
        if (!latch.await(30, TimeUnit.SECONDS)) {
            Assert.fail("Incomplete! Went through " + latch.getCount() + " iterations");
        } else {
            Assert.assertEquals(NITERS, counter.get());
        }
    }
}
