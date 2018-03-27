

package io.reactivex.internal.operators.flowable;

import org.reactivestreams.*;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class FlowableElementAtMaybe<T> extends Maybe<T> implements FuseToFlowable<T> {
    final Flowable<T> source;

    final long index;

    public FlowableElementAtMaybe(Flowable<T> source, long index) {
        this.source = source;
        this.index = index;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> s) {
        source.subscribe(new ElementAtSubscriber<T>(s, index));
    }

    @Override
    public Flowable<T> fuseToFlowable() {
        return RxJavaPlugins.onAssembly(new FlowableElementAt<T>(source, index, null, false));
    }

    static final class ElementAtSubscriber<T> implements FlowableSubscriber<T>, Disposable {

        final MaybeObserver<? super T> actual;

        final long index;

        Subscription s;

        long count;

        boolean done;

        ElementAtSubscriber(MaybeObserver<? super T> actual, long index) {
            this.actual = actual;
            this.index = index;
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
            long c = count;
            if (c == index) {
                done = true;
                s.cancel();
                s = SubscriptionHelper.CANCELLED;
                actual.onSuccess(t);
                return;
            }
            count = c + 1;
        }

        @Override
        public void onError(Throwable t) {
            if (done) {
                RxJavaPlugins.onError(t);
                return;
            }
            done = true;
            s = SubscriptionHelper.CANCELLED;
            actual.onError(t);
        }

        @Override
        public void onComplete() {
            s = SubscriptionHelper.CANCELLED;
            if (!done) {
                done = true;
                actual.onComplete();
            }
        }

        @Override
        public void dispose() {
            s.cancel();
            s = SubscriptionHelper.CANCELLED;
        }

        @Override
        public boolean isDisposed() {
            return s == SubscriptionHelper.CANCELLED;
        }

    }
}
