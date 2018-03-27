

package io.reactivex.internal.operators.maybe;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import io.reactivex.*;
import io.reactivex.disposables.*;
import io.reactivex.exceptions.TestException;
import io.reactivex.plugins.RxJavaPlugins;

public class MaybeCreateTest {

    @Test
    public void callbackThrows() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                throw new TestException();
            }
        })
        .test()
        .assertFailure(TestException.class);
    }

    @Test
    public void onSuccessNull() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                e.onSuccess(null);
            }
        })
        .test()
        .assertFailure(NullPointerException.class);
    }

    @Test
    public void onErrorNull() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                e.onError(null);
            }
        })
        .test()
        .assertFailure(NullPointerException.class);
    }

    @Test
    public void dispose() {
        TestHelper.checkDisposed(Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                e.onSuccess(1);
            }
        }));
    }

    @Test
    public void onSuccessThrows() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                Disposable d = Disposables.empty();
                e.setDisposable(d);

                try {
                    e.onSuccess(1);
                    fail("Should have thrown");
                } catch (TestException ex) {
                                    }

                assertTrue(d.isDisposed());
                assertTrue(e.isDisposed());
            }
        }).subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {
                throw new TestException();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void onErrorThrows() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                Disposable d = Disposables.empty();
                e.setDisposable(d);

                try {
                    e.onError(new IOException());
                    fail("Should have thrown");
                } catch (TestException ex) {
                                    }

                assertTrue(d.isDisposed());
                assertTrue(e.isDisposed());
            }
        }).subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {

            }

            @Override
            public void onError(Throwable e) {
                throw new TestException();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void onCompleteThrows() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                Disposable d = Disposables.empty();
                e.setDisposable(d);

                try {
                    e.onComplete();
                    fail("Should have thrown");
                } catch (TestException ex) {
                                    }

                assertTrue(d.isDisposed());
                assertTrue(e.isDisposed());
            }
        }).subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                throw new TestException();
            }
        });
    }

    @Test
    public void onSuccessThrows2() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                try {
                    e.onSuccess(1);
                    fail("Should have thrown");
                } catch (TestException ex) {
                                    }

                assertTrue(e.isDisposed());
            }
        }).subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {
                throw new TestException();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void onErrorThrows2() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                try {
                    e.onError(new IOException());
                    fail("Should have thrown");
                } catch (TestException ex) {
                                    }

                assertTrue(e.isDisposed());
            }
        }).subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {

            }

            @Override
            public void onError(Throwable e) {
                throw new TestException();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void onCompleteThrows2() {
        Maybe.create(new MaybeOnSubscribe<Object>() {
            @Override
            public void subscribe(MaybeEmitter<Object> e) throws Exception {
                try {
                    e.onComplete();
                    fail("Should have thrown");
                } catch (TestException ex) {
                                    }

                assertTrue(e.isDisposed());
            }
        }).subscribe(new MaybeObserver<Object>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Object value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                throw new TestException();
            }
        });
    }

    @Test
    public void tryOnError() {
        List<Throwable> errors = TestHelper.trackPluginErrors();
        try {
            final Boolean[] response = { null };
            Maybe.create(new MaybeOnSubscribe<Object>() {
                @Override
                public void subscribe(MaybeEmitter<Object> e) throws Exception {
                    e.onSuccess(1);
                    response[0] = e.tryOnError(new TestException());
                }
            })
            .test()
            .assertResult(1);

            assertFalse(response[0]);

            assertTrue(errors.toString(), errors.isEmpty());
        } finally {
            RxJavaPlugins.reset();
        }
    }
}
