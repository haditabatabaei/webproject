

package io.reactivex.internal.operators.maybe;

import static org.junit.Assert.*;

import java.util.concurrent.*;

import org.junit.Test;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.TestException;
import io.reactivex.functions.*;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class MaybeUnsubscribeOnTest {

    @Test
    public void normal() throws Exception {
        PublishProcessor<Integer> pp = PublishProcessor.create();

        final String[] name = { null };

        final CountDownLatch cdl = new CountDownLatch(1);

        pp.doOnCancel(new Action() {
            @Override
            public void run() throws Exception {
                name[0] = Thread.currentThread().getName();
                cdl.countDown();
            }
        })
        .singleElement()
        .unsubscribeOn(Schedulers.single())
        .test(true)
        ;

        assertTrue(cdl.await(5, TimeUnit.SECONDS));

        int times = 10;

        while (times-- > 0 && pp.hasSubscribers()) {
            Thread.sleep(100);
        }

        assertFalse(pp.hasSubscribers());

        assertNotEquals(Thread.currentThread().getName(), name[0]);
    }

    @Test
    public void just() {
        Maybe.just(1)
        .unsubscribeOn(Schedulers.single())
        .test()
        .assertResult(1);
    }

    @Test
    public void error() {
        Maybe.<Integer>error(new TestException())
        .unsubscribeOn(Schedulers.single())
        .test()
        .assertFailure(TestException.class);
    }

    @Test
    public void empty() {
        Maybe.empty()
        .unsubscribeOn(Schedulers.single())
        .test()
        .assertResult();
    }

    @Test
    public void dispose() {
        TestHelper.checkDisposed(Maybe.just(1)
        .unsubscribeOn(Schedulers.single()));
    }

    @Test
    public void doubleOnSubscribe() {
        TestHelper.checkDoubleOnSubscribeMaybe(new Function<Maybe<Object>, MaybeSource<Object>>() {
            @Override
            public MaybeSource<Object> apply(Maybe<Object> v) throws Exception {
                return v.unsubscribeOn(Schedulers.single());
            }
        });
    }

    @Test
    public void disposeRace() {
        for (int i = 0; i < TestHelper.RACE_DEFAULT_LOOPS; i++) {
            PublishProcessor<Integer> pp = PublishProcessor.create();

            final Disposable[] ds = { null };
            pp.singleElement().unsubscribeOn(Schedulers.computation())
            .subscribe(new MaybeObserver<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {
                    ds[0] = d;
                }

                @Override
                public void onSuccess(Integer value) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    ds[0].dispose();
                }
            };

            TestHelper.race(r, r);
        }
    }
}
