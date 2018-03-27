

package io.reactivex.internal.operators.flowable;

import java.util.NoSuchElementException;

import org.reactivestreams.*;

import io.reactivex.*;
import io.reactivex.internal.subscriptions.*;
import io.reactivex.plugins.RxJavaPlugins;

public final class FlowableElementAt<T> extends AbstractFlowableWithUpstream<T, T> {
    final long index;
    final T defaultValue;
    final boolean errorOnFewer;

    public FlowableElementAt(Flowable<T> source, long index, T defaultValue, boolean errorOnFewer) {
        super(source);
        this.index = index;
        this.defaultValue = defaultValue;
        this.errorOnFewer = errorOnFewer;
    }

    @Override
    protected void subscribeActual(Subscriber<? super T> s) {
        source.subscribe(new ElementAtSubscriber<T>(s, index, defaultValue, errorOnFewer));
    }

    static final class ElementAtSubscriber<T> extends DeferredScalarSubscription<T> implements FlowableSubscriber<T> {

        private static final long serialVersionUID = 4066607327284737757L;

        final long index;
        final T defaultValue;
        final boolean errorOnFewer;

        Subscription s;

        long count;

        boolean done;

        ElementAtSubscriber(Subscriber<? super T> actual, long index, T defaultValue, boolean errorOnFewer) {
            super(actual);
            this.index = index;
            this.defaultValue = defaultValue;
            this.errorOnFewer = errorOnFewer;
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
                complete(t);
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
            actual.onError(t);
        }

        @Override
        public void onComplete() {
            if (!done) {
                done = true;
                T v = defaultValue;
                if (v == null) {
                    if (errorOnFewer) {
                        actual.onError(new NoSuchElementException());
                    } else {
                        actual.onComplete();
                    }
                } else {
                    complete(v);
                }
            }
        }

        @Override
        public void cancel() {
            super.cancel();
            s.cancel();
        }
    }
}
