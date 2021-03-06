

package io.reactivex.internal.operators.observable;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.*;

import org.junit.Test;

import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.QueueFuseable;
import io.reactivex.observers.*;

public class ObservableRangeTest {

    @Test
    public void testRangeStartAt2Count3() {
        Observer<Integer> observer = TestHelper.mockObserver();

        Observable.range(2, 3).subscribe(observer);

        verify(observer, times(1)).onNext(2);
        verify(observer, times(1)).onNext(3);
        verify(observer, times(1)).onNext(4);
        verify(observer, never()).onNext(5);
        verify(observer, never()).onError(any(Throwable.class));
        verify(observer, times(1)).onComplete();
    }

    @Test
    public void testRangeUnsubscribe() {
        Observer<Integer> observer = TestHelper.mockObserver();

        final AtomicInteger count = new AtomicInteger();

        Observable.range(1, 1000).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer t1) {
                count.incrementAndGet();
            }
        })
        .take(3).subscribe(observer);

        verify(observer, times(1)).onNext(1);
        verify(observer, times(1)).onNext(2);
        verify(observer, times(1)).onNext(3);
        verify(observer, never()).onNext(4);
        verify(observer, never()).onError(any(Throwable.class));
        verify(observer, times(1)).onComplete();
        assertEquals(3, count.get());
    }

    @Test
    public void testRangeWithZero() {
        Observable.range(1, 0);
    }

    @Test
    public void testRangeWithOverflow2() {
        Observable.range(Integer.MAX_VALUE, 0);
    }

    @Test
    public void testRangeWithOverflow3() {
        Observable.range(1, Integer.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeWithOverflow4() {
        Observable.range(2, Integer.MAX_VALUE);
    }

    @Test
    public void testRangeWithOverflow5() {
        assertFalse(Observable.range(Integer.MIN_VALUE, 0).blockingIterable().iterator().hasNext());
    }

    @Test
    public void testNoBackpressure() {
        ArrayList<Integer> list = new ArrayList<Integer>(Flowable.bufferSize() * 2);
        for (int i = 1; i <= Flowable.bufferSize() * 2 + 1; i++) {
            list.add(i);
        }

        Observable<Integer> o = Observable.range(1, list.size());

        TestObserver<Integer> to = new TestObserver<Integer>();

        o.subscribe(to);

        to.assertValueSequence(list);
        to.assertTerminated();
    }

    @Test
    public void testEmptyRangeSendsOnCompleteEagerlyWithRequestZero() {
        final AtomicBoolean completed = new AtomicBoolean(false);
        Observable.range(1, 0).subscribe(new DefaultObserver<Integer>() {

            @Override
            public void onStart() {
            }

            @Override
            public void onComplete() {
                completed.set(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer t) {

            }});
        assertTrue(completed.get());
    }

    @Test(timeout = 1000)
    public void testNearMaxValueWithoutBackpressure() {
        TestObserver<Integer> to = new TestObserver<Integer>();
        Observable.range(Integer.MAX_VALUE - 1, 2).subscribe(to);

        to.assertComplete();
        to.assertNoErrors();
        to.assertValues(Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
    }

    @Test
    public void negativeCount() {
        try {
            Observable.range(1, -1);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertEquals("count >= 0 required but it was -1", ex.getMessage());
        }
    }

    @Test
    public void requestWrongFusion() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ASYNC);

        Observable.range(1, 5)
        .subscribe(to);

        ObserverFusion.assertFusion(to, QueueFuseable.NONE)
        .assertResult(1, 2, 3, 4, 5);
    }
}
