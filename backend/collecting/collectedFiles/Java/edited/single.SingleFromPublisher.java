

package io.reactivex.internal.operators.single;

import java.util.NoSuchElementException;

import org.reactivestreams.*;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class SingleFromPublisher<T> extends Single<T> {

    final Publisher<? extends T> publisher;

    public SingleFromPublisher(Publisher<? extends T> publisher) {
        this.publisher = publisher;
    }

    @Override
    protected void subscribeActual(final SingleObserver<? super T> s) {
        publisher.subscribe(new ToSingleObserver<T>(s));
    }

    static final class ToSingleObserver<T> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super T> actual;

        Subscription s;

        T value;

        boolean done;

        volatile boolean disposed;

        ToSingleObserver(SingleObserver<? super T> actual) {
            this.actual = actual;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.s, s)) {
                this.s = s;

                actual.onSubscribe(this);

                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t) {
            if (done) {
                return;
            }
            if (value != null) {
                s.cancel();
                done = true;
                this.value = null;
                actual.onError(new IndexOutOfBoundsException("Too many elements in the Publisher"));
            } else {
                value = t;
            }
        }

        @Override
        public void onError(Throwable t) {
            if (done) {
                RxJavaPlugins.onError(t);
                return;
            }
            done = true;
            this.value = null;
            actual.onError(t);
        }

        @Override
        public void onComplete() {
            if (done) {
                return;
            }
            done = true;
            T v = this.value;
            this.value = null;
            if (v == null) {
                actual.onError(new NoSuchElementException("The source Publisher is empty"));
            } else {
                actual.onSuccess(v);
            }
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }

        @Override
        public void dispose() {
            disposed = true;
            s.cancel();
        }
    }
}
