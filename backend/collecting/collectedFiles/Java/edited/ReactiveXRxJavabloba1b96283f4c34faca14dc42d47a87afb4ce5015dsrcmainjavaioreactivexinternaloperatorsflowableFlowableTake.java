

package io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicBoolean;

import org.reactivestreams.*;

import io.reactivex.*;
import io.reactivex.internal.subscriptions.*;
import io.reactivex.plugins.RxJavaPlugins;

public final class FlowableTake<T> extends AbstractFlowableWithUpstream<T, T> {
    final long limit;
    public FlowableTake(Flowable<T> source, long limit) {
        super(source);
        this.limit = limit;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> s) {
        source.subscribe(new TakeSubscriber<T>(s, limit));
    }

    static final class TakeSubscriber<T> extends AtomicBoolean implements FlowableSubscriber<T>, Subscription {

        private static final long serialVersionUID = -5636543848937116287L;
        boolean done;
        Subscription subscription;
        final Subscriber<? super T> actual;
        final long limit;
        long remaining;
        TakeSubscriber(Subscriber<? super T> actual, long limit) {
            this.actual = actual;
            this.limit = limit;
            this.remaining = limit;
        }
        @Override
        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.subscription, s)) {
                subscription = s;
                if (limit == 0L) {
                    s.cancel();
                    done = true;
                    EmptySubscription.complete(actual);
                } else {
                    actual.onSubscribe(this);
                }
            }
        }
        @Override
        public void onNext(T t) {
            if (!done && remaining-- > 0) {
                boolean stop = remaining == 0;
                actual.onNext(t);
                if (stop) {
                    subscription.cancel();
                    onComplete();
                }
            }
        }
        @Override
        public void onError(Throwable t) {
            if (!done) {
                done = true;
                subscription.cancel();
                actual.onError(t);
            } else {
                RxJavaPlugins.onError(t);
            }
        }
        @Override
        public void onComplete() {
            if (!done) {
                done = true;
                actual.onComplete();
            }
        }
        @Override
        public void request(long n) {
            if (!SubscriptionHelper.validate(n)) {
                return;
            }
            if (!get() && compareAndSet(false, true)) {
                if (n >= limit) {
                    subscription.request(Long.MAX_VALUE);
                    return;
                }
            }
            subscription.request(n);
        }
        @Override
        public void cancel() {
            subscription.cancel();
        }
    }
}
