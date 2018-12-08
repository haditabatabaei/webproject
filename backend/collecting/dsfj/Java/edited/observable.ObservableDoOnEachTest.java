

package io.reactivex.internal.operators.observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.*;

import io.reactivex.*;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.*;
import io.reactivex.functions.*;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.fuseable.QueueFuseable;
import io.reactivex.observers.*;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;

public class ObservableDoOnEachTest {

    Observer<String> subscribedObserver;
    Observer<String> sideEffectObserver;

    @Before
    public void before() {
        subscribedObserver = TestHelper.mockObserver();
        sideEffectObserver = TestHelper.mockObserver();
    }

    @Test
    public void testDoOnEach() {
        Observable<String> base = Observable.just("a", "b", "c");
        Observable<String> doOnEach = base.doOnEach(sideEffectObserver);

        doOnEach.subscribe(subscribedObserver);

                verify(subscribedObserver, never()).onError(any(Throwable.class));
        verify(subscribedObserver, times(1)).onNext("a");
        verify(subscribedObserver, times(1)).onNext("b");
        verify(subscribedObserver, times(1)).onNext("c");
        verify(subscribedObserver, times(1)).onComplete();

                verify(sideEffectObserver, never()).onError(any(Throwable.class));
        verify(sideEffectObserver, times(1)).onNext("a");
        verify(sideEffectObserver, times(1)).onNext("b");
        verify(sideEffectObserver, times(1)).onNext("c");
        verify(sideEffectObserver, times(1)).onComplete();
    }

    @Test
    public void testDoOnEachWithError() {
        Observable<String> base = Observable.just("one", "fail", "two", "three", "fail");
        Observable<String> errs = base.map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                if ("fail".equals(s)) {
                    throw new RuntimeException("Forced Failure");
                }
                return s;
            }
        });

        Observable<String> doOnEach = errs.doOnEach(sideEffectObserver);

        doOnEach.subscribe(subscribedObserver);
        verify(subscribedObserver, times(1)).onNext("one");
        verify(subscribedObserver, never()).onNext("two");
        verify(subscribedObserver, never()).onNext("three");
        verify(subscribedObserver, never()).onComplete();
        verify(subscribedObserver, times(1)).onError(any(Throwable.class));

        verify(sideEffectObserver, times(1)).onNext("one");
        verify(sideEffectObserver, never()).onNext("two");
        verify(sideEffectObserver, never()).onNext("three");
        verify(sideEffectObserver, never()).onComplete();
        verify(sideEffectObserver, times(1)).onError(any(Throwable.class));
    }

    @Test
    public void testDoOnEachWithErrorInCallback() {
        Observable<String> base = Observable.just("one", "two", "fail", "three");
        Observable<String> doOnEach = base.doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) {
                if ("fail".equals(s)) {
                    throw new RuntimeException("Forced Failure");
                }
            }
        });

        doOnEach.subscribe(subscribedObserver);
        verify(subscribedObserver, times(1)).onNext("one");
        verify(subscribedObserver, times(1)).onNext("two");
        verify(subscribedObserver, never()).onNext("three");
        verify(subscribedObserver, never()).onComplete();
        verify(subscribedObserver, times(1)).onError(any(Throwable.class));

    }

    @Test
    public void testIssue1451Case1() {
                final int expectedCount = 3;
        final AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < expectedCount; i++) {
            Observable
                    .just(Boolean.TRUE, Boolean.FALSE)
                    .takeWhile(new Predicate<Boolean>() {
                        @Override
                        public boolean test(Boolean value) {
                            return value;
                        }
                    })
                    .toList()
                    .doOnSuccess(new Consumer<List<Boolean>>() {
                        @Override
                        public void accept(List<Boolean> booleans) {
                            count.incrementAndGet();
                        }
                    })
                    .subscribe();
        }
        assertEquals(expectedCount, count.get());
    }

    @Test
    public void testIssue1451Case2() {
                final int expectedCount = 3;
        final AtomicInteger count = new AtomicInteger();
        for (int i = 0; i < expectedCount; i++) {
            Observable
                    .just(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE)
                    .takeWhile(new Predicate<Boolean>() {
                        @Override
                        public boolean test(Boolean value) {
                            return value;
                        }
                    })
                    .toList()
                    .doOnSuccess(new Consumer<List<Boolean>>() {
                        @Override
                        public void accept(List<Boolean> booleans) {
                            count.incrementAndGet();
                        }
                    })
                    .subscribe();
        }
        assertEquals(expectedCount, count.get());
    }

    
    @Test
    public void onErrorThrows() {
        TestObserver<Object> to = TestObserver.create();

        Observable.error(new TestException())
        .doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) {
                throw new TestException();
            }
        }).subscribe(to);

        to.assertNoValues();
        to.assertNotComplete();
        to.assertError(CompositeException.class);

        CompositeException ex = (CompositeException)to.errors().get(0);

        List<Throwable> exceptions = ex.getExceptions();
        assertEquals(2, exceptions.size());
        Assert.assertTrue(exceptions.get(0) instanceof TestException);
        Assert.assertTrue(exceptions.get(1) instanceof TestException);
    }

    @Test
    public void ignoreCancel() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        try {
            Observable.wrap(new ObservableSource<Object>() {
                @Override
                public void subscribe(Observer<? super Object> s) {
                    s.onSubscribe(Disposables.empty());
                    s.onNext(1);
                    s.onNext(2);
                    s.onError(new IOException());
                    s.onComplete();
                }
            })
            .doOnNext(new Consumer<Object>() {
                @Override
                public void accept(Object e) throws Exception {
                    throw new TestException();
                }
            })
            .test()
            .assertFailure(TestException.class);

            TestHelper.assertUndeliverable(errors, 0, IOException.class);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void onErrorAfterCrash() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        try {
            Observable.wrap(new ObservableSource<Object>() {
                @Override
                public void subscribe(Observer<? super Object> s) {
                    s.onSubscribe(Disposables.empty());
                    s.onError(new TestException());
                }
            })
            .doAfterTerminate(new Action() {
                @Override
                public void run() throws Exception {
                    throw new IOException();
                }
            })
            .test()
            .assertFailure(TestException.class);

            TestHelper.assertUndeliverable(errors, 0, IOException.class);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void onCompleteAfterCrash() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        try {
            Observable.wrap(new ObservableSource<Object>() {
                @Override
                public void subscribe(Observer<? super Object> s) {
                    s.onSubscribe(Disposables.empty());
                    s.onComplete();
                }
            })
            .doAfterTerminate(new Action() {
                @Override
                public void run() throws Exception {
                    throw new IOException();
                }
            })
            .test()
            .assertResult();

            TestHelper.assertUndeliverable(errors, 0, IOException.class);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void onCompleteCrash() {
        Observable.wrap(new ObservableSource<Object>() {
            @Override
            public void subscribe(Observer<? super Object> s) {
                s.onSubscribe(Disposables.empty());
                s.onComplete();
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                throw new IOException();
            }
        })
        .test()
        .assertFailure(IOException.class);
    }

    @Test
    public void ignoreCancelConditional() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        try {
            Observable.wrap(new ObservableSource<Object>() {
                @Override
                public void subscribe(Observer<? super Object> s) {
                    s.onSubscribe(Disposables.empty());
                    s.onNext(1);
                    s.onNext(2);
                    s.onError(new IOException());
                    s.onComplete();
                }
            })
            .doOnNext(new Consumer<Object>() {
                @Override
                public void accept(Object e) throws Exception {
                    throw new TestException();
                }
            })
            .filter(Functions.alwaysTrue())
            .test()
            .assertFailure(TestException.class);

            TestHelper.assertUndeliverable(errors, 0, IOException.class);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void onErrorAfterCrashConditional() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        try {
            Observable.wrap(new ObservableSource<Object>() {
                @Override
                public void subscribe(Observer<? super Object> s) {
                    s.onSubscribe(Disposables.empty());
                    s.onError(new TestException());
                }
            })
            .doAfterTerminate(new Action() {
                @Override
                public void run() throws Exception {
                    throw new IOException();
                }
            })
            .filter(Functions.alwaysTrue())
            .test()
            .assertFailure(TestException.class);

            TestHelper.assertUndeliverable(errors, 0, IOException.class);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void onCompleteAfter() {
        final int[] call = { 0 };
        Observable.just(1)
        .doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                call[0]++;
            }
        })
        .test()
        .assertResult(1);

        assertEquals(1, call[0]);
    }

    @Test
    public void onCompleteAfterCrashConditional() {
        List<Throwable> errors = TestHelper.trackPluginErrors();

        try {
            Observable.wrap(new ObservableSource<Object>() {
                @Override
                public void subscribe(Observer<? super Object> s) {
                    s.onSubscribe(Disposables.empty());
                    s.onComplete();
                }
            })
            .doAfterTerminate(new Action() {
                @Override
                public void run() throws Exception {
                    throw new IOException();
                }
            })
            .filter(Functions.alwaysTrue())
            .test()
            .assertResult();

            TestHelper.assertUndeliverable(errors, 0, IOException.class);
        } finally {
            RxJavaPlugins.reset();
        }
    }

    @Test
    public void onCompleteCrashConditional() {
        Observable.wrap(new ObservableSource<Object>() {
            @Override
            public void subscribe(Observer<? super Object> s) {
                s.onSubscribe(Disposables.empty());
                s.onComplete();
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                throw new IOException();
            }
        })
        .filter(Functions.alwaysTrue())
        .test()
        .assertFailure(IOException.class);
    }

    @Test
    public void onErrorOnErrorCrashConditional() {
        TestObserver<Object> to = Observable.error(new TestException("Outer"))
        .doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {
                throw new TestException("Inner");
            }
        })
        .filter(Functions.alwaysTrue())
        .test()
        .assertFailure(CompositeException.class);

        List<Throwable> errors = TestHelper.compositeList(to.errors().get(0));

        TestHelper.assertError(errors, 0, TestException.class, "Outer");
        TestHelper.assertError(errors, 1, TestException.class, "Inner");
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fused() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0, 0 };

        Observable.range(1, 5)
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                call[0]++;
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[1]++;
            }
        })
        .subscribe(to);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.SYNC))
        .assertResult(1, 2, 3, 4, 5);

        assertEquals(5, call[0]);
        assertEquals(1, call[1]);
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fusedOnErrorCrash() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0 };

        Observable.range(1, 5)
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                throw new TestException();
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[0]++;
            }
        })
        .subscribe(to);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.SYNC))
        .assertFailure(TestException.class);

        assertEquals(0, call[0]);
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fusedConditional() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0, 0 };

        Observable.range(1, 5)
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                call[0]++;
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[1]++;
            }
        })
        .filter(Functions.alwaysTrue())
        .subscribe(to);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.SYNC))
        .assertResult(1, 2, 3, 4, 5);

        assertEquals(5, call[0]);
        assertEquals(1, call[1]);
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fusedOnErrorCrashConditional() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0 };

        Observable.range(1, 5)
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                throw new TestException();
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[0]++;
            }
        })
        .filter(Functions.alwaysTrue())
        .subscribe(to);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.SYNC))
        .assertFailure(TestException.class);

        assertEquals(0, call[0]);
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fusedAsync() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0, 0 };

        UnicastSubject<Integer> up = UnicastSubject.create();

        up
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                call[0]++;
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[1]++;
            }
        })
        .subscribe(to);

        TestHelper.emit(up, 1, 2, 3, 4, 5);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.ASYNC))
        .assertResult(1, 2, 3, 4, 5);

        assertEquals(5, call[0]);
        assertEquals(1, call[1]);
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fusedAsyncConditional() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0, 0 };

        UnicastSubject<Integer> up = UnicastSubject.create();

        up
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                call[0]++;
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[1]++;
            }
        })
        .filter(Functions.alwaysTrue())
        .subscribe(to);

        TestHelper.emit(up, 1, 2, 3, 4, 5);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.ASYNC))
        .assertResult(1, 2, 3, 4, 5);

        assertEquals(5, call[0]);
        assertEquals(1, call[1]);
    }

    @Test
    @Ignore("Fusion not supported yet")     public void fusedAsyncConditional2() {
        TestObserver<Integer> to = ObserverFusion.newTest(QueueFuseable.ANY);

        final int[] call = { 0, 0 };

        UnicastSubject<Integer> up = UnicastSubject.create();

        up.hide()
        .doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer v) throws Exception {
                call[0]++;
            }
        })
        .doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                call[1]++;
            }
        })
        .filter(Functions.alwaysTrue())
        .subscribe(to);

        TestHelper.emit(up, 1, 2, 3, 4, 5);

        to.assertOf(ObserverFusion.<Integer>assertFuseable())
        .assertOf(ObserverFusion.<Integer>assertFusionMode(QueueFuseable.NONE))
        .assertResult(1, 2, 3, 4, 5);

        assertEquals(5, call[0]);
        assertEquals(1, call[1]);
    }

    @Test
    public void dispose() {
        TestHelper.checkDisposed(Observable.just(1).doOnEach(new TestObserver<Integer>()));
    }

    @Test
    public void doubleOnSubscribe() {
        TestHelper.checkDoubleOnSubscribeObservable(new Function<Observable<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(Observable<Object> o) throws Exception {
                return o.doOnEach(new TestObserver<Object>());
            }
        });
    }
}