

package io.reactivex.internal.subscribers;

import java.util.concurrent.atomic.AtomicReference;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.fuseable.*;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;


public final class InnerQueuedSubscriber<T>
extends AtomicReference<Subscription>
implements FlowableSubscriber<T>, Subscription {


    private static final long serialVersionUID = 22876611072430776L;

    final InnerQueuedSubscriberSupport<T> parent;

    final int prefetch;

    final int limit;

    volatile SimpleQueue<T> queue;

    volatile boolean done;

    long produced;

    int fusionMode;

    public InnerQueuedSubscriber(InnerQueuedSubscriberSupport<T> parent, int prefetch) {
        this.parent = parent;
        this.prefetch = prefetch;
        this.limit = prefetch - (prefetch >> 2);
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (SubscriptionHelper.setOnce(this, s)) {
            if (s instanceof QueueSubscription) {
                @SuppressWarnings("unchecked")
                QueueSubscription<T> qs = (QueueSubscription<T>) s;

                int m = qs.requestFusion(QueueSubscription.ANY);
                if (m == QueueSubscription.SYNC) {
                    fusionMode = m;
                    queue = qs;
                    done = true;
                    parent.innerComplete(this);
                    return;
                }
                if (m == QueueSubscription.ASYNC) {
                    fusionMode = m;
                    queue = qs;
                    QueueDrainHelper.request(s, prefetch);
                    return;
                }
            }

            queue = QueueDrainHelper.createQueue(prefetch);

            QueueDrainHelper.request(s, prefetch);
        }
    }

    @Override
    public void onNext(T t) {
        if (fusionMode == QueueSubscription.NONE) {
            parent.innerNext(this, t);
        } else {
            parent.drain();
        }
    }

    @Override
    public void onError(Throwable t) {
        parent.innerError(this, t);
    }

    @Override
    public void onComplete() {
        parent.innerComplete(this);
    }

    @Override
    public void request(long n) {
        if (fusionMode != QueueSubscription.SYNC) {
            long p = produced + n;
            if (p >= limit) {
                produced = 0L;
                get().request(p);
            } else {
                produced = p;
            }
        }
    }

    public void requestOne() {
        if (fusionMode != QueueSubscription.SYNC) {
            long p = produced + 1;
            if (p == limit) {
                produced = 0L;
                get().request(p);
            } else {
                produced = p;
            }
        }
    }

    @Override
    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone() {
        this.done = true;
    }

    public SimpleQueue<T> queue() {
        return queue;
    }
}
