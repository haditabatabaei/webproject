

package io.reactivex.internal.operators.maybe;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import io.reactivex.*;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class MaybeFromRunnableTest {
    @Test(expected = NullPointerException.class)
    public void fromRunnableNull() {
        Maybe.fromRunnable(null);
    }

    @Test
    public void fromRunnable() {
        final AtomicInteger atomicInteger = new AtomicInteger();

        Maybe.fromRunnable(new Runnable() {
            @Override
            public void run() {
                atomicInteger.incrementAndGet();
            }
        })
            .test()
            .assertResult();

        assertEquals(1, atomicInteger.get());
    }

    @Test
    public void fromRunnableTwice() {
        final AtomicInteger atomicInteger = new AtomicInteger();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                atomicInteger.incrementAndGet();
            }
        };

        Maybe.fromRunnable(run)
            .test()
            .assertResult();

        assertEquals(1, atomicInteger.get());

        Maybe.fromRunnable(run)
            .test()
            .assertResult();

        assertEquals(2, atomicInteger.get());
    }

    @Test
    public void fromRunnableInvokesLazy() {
        final AtomicInteger atomicInteger = new AtomicInteger();

        final Maybe<Object> maybe = Maybe.fromRunnable(new Runnable() {
            @Override public void run() {
                atomicInteger.incrementAndGet();
            }
        });

        assertEquals(0, atomicInteger.get());

        maybe
            .test()
            .assertResult();

        assertEquals(1, atomicInteger.get());
    }

    @Test
    public void fromRunnableThrows() {
        Maybe.fromRunnable(new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException();
            }
        })
            .test()
            .assertFailure(UnsupportedOperationException.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void callable() throws Exception {
        final int[] counter = { 0 };

        Maybe<Void> m = Maybe.fromRunnable(new Runnable() {
            @Override
            public void run() {
                counter[0]++;
            }
        });

        assertTrue(m.getClass().toString(), m instanceof Callable);

        assertNull(((Callable<Void>)m).call());

        assertEquals(1, counter[0]);
    }

    @Test
    public void noErrorLoss() throws Exception {
        List<Throwable> errors = TestHelper.trackPluginErrors();
        try {
            final CountDownLatch cdl1 = new CountDownLatch(1);
            final CountDownLatch cdl2 = new CountDownLatch(1);

            TestObserver<Object> to = Maybe.fromRunnable(new Runnable() {
                @Override
                public void run() {
                    cdl1.countDown();
                    try {
                        cdl2.await(5, TimeUnit.SECONDS);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }).subscribeOn(Schedulers.single()).test();

            assertTrue(cdl1.await(5, TimeUnit.SECONDS));

            to.cancel();

            int timeout = 10;

            while (timeout-- > 0 && errors.isEmpty()) {
                Thread.sleep(100);
            }

            TestHelper.assertUndeliverable(errors, 0, RuntimeException.class);

            assertTrue(errors.get(0).toString(), errors.get(0).getCause().getCause() instanceof InterruptedException);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void disposedUpfront() {
        Runnable run = mock(Runnable.class);

        Maybe.fromRunnable(run)
        .test(true)
        .assertEmpty();

        verify(run, never()).run();
    }

    @Test
    public void cancelWhileRunning() {
        final TestObserver<Object> to = new TestObserver<Object>();

        Maybe.fromRunnable(new Runnable() {
            @Override
            public void run() {
                to.dispose();
            }
        })
        .subscribeWith(to)
        .assertEmpty();

        assertTrue(to.isDisposed());
    }
}
