
package io.reactivex.internal.operators.flowable;

import org.reactivestreams.*;

import io.reactivex.*;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.*;
import io.reactivex.internal.subscriptions.*;
import io.reactivex.plugins.RxJavaPlugins;

public final class FlowableDoOnLifecycle<T> extends AbstractFlowableWithUpstream<T, T> {
    private final Consumer<? super Subscription> onSubscribe;
    private final LongConsumer onRequest;
    private final Action onCancel;

    public FlowableDoOnLifecycle(Flowable<T> source, Consumer<? super Subscription> onSubscribe,
            LongConsumer onRequest, Action onCancel) {
        super(source);
        this.onSubscribe = onSubscribe;
        this.onRequest = onRequest;
        this.onCancel = onCancel;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> s) {
        source.subscribe(new SubscriptionLambdaSubscriber<T>(s, onSubscribe, onRequest, onCancel));
    }

    static final class SubscriptionLambdaSubscriber<T> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super T> actual;
        final Consumer<? super Subscription> onSubscribe;
        final LongConsumer onRequest;
        final Action onCancel;

        Subscription s;

        SubscriptionLambdaSubscriber(Subscriber<? super T> actual,
                Consumer<? super Subscription> onSubscribe,
                LongConsumer onRequest,
                Action onCancel) {
            this.actual = actual;
            this.onSubscribe = onSubscribe;
            this.onCancel = onCancel;
            this.onRequest = onRequest;
        }

        @Override
        public void onSubscribe(Subscription s) {
                        try {
                onSubscribe.accept(s);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                s.cancel();
                this.s = SubscriptionHelper.CANCELLED;
                EmptySubscription.error(e, actual);
                return;
            }
            if (SubscriptionHelper.validate(this.s, s)) {
                this.s = s;
                actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t) {
            actual.onNext(t);
        }

        @Override
        public void onError(Throwable t) {
            if (s != SubscriptionHelper.CANCELLED) {
                actual.onError(t);
            } else {
                RxJavaPlugins.onError(t);
            }
        }

        @Override
        public void onComplete() {
            if (s != SubscriptionHelper.CANCELLED) {
                actual.onComplete();
            }
        }

        @Override
        public void request(long n) {
            try {
                onRequest.accept(n);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
            s.request(n);
        }

        @Override
        public void cancel() {
            try {
                onCancel.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
            s.cancel();
        }
    }
}