

package io.reactivex.internal.operators.mixed;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.*;
import io.reactivex.annotations.Experimental;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.*;
import io.reactivex.plugins.RxJavaPlugins;


@Experimental
public final class ObservableSwitchMapCompletable<T> extends Completable {

    final Observable<T> source;

    final Function<? super T, ? extends CompletableSource> mapper;

    final boolean delayErrors;

    public ObservableSwitchMapCompletable(Observable<T> source,
            Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors) {
        this.source = source;
        this.mapper = mapper;
        this.delayErrors = delayErrors;
    }

    @Override
    protected void subscribeActual(CompletableObserver s) {
        if (!ScalarXMapZHelper.tryAsCompletable(source, mapper, s)) {
            source.subscribe(new SwitchMapCompletableObserver<T>(s, mapper, delayErrors));
        }
    }

    static final class SwitchMapCompletableObserver<T> implements Observer<T>, Disposable {

        final CompletableObserver downstream;

        final Function<? super T, ? extends CompletableSource> mapper;

        final boolean delayErrors;

        final AtomicThrowable errors;

        final AtomicReference<SwitchMapInnerObserver> inner;

        static final SwitchMapInnerObserver INNER_DISPOSED = new SwitchMapInnerObserver(null);

        volatile boolean done;

        Disposable upstream;

        SwitchMapCompletableObserver(CompletableObserver downstream,
                Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors) {
            this.downstream = downstream;
            this.mapper = mapper;
            this.delayErrors = delayErrors;
            this.errors = new AtomicThrowable();
            this.inner = new AtomicReference<SwitchMapInnerObserver>();
        }

        @Override
        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(upstream, s)) {
                this.upstream = s;
                downstream.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t) {
            CompletableSource c;

            try {
                c = ObjectHelper.requireNonNull(mapper.apply(t), "The mapper returned a null CompletableSource");
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                upstream.dispose();
                onError(ex);
                return;
            }

            SwitchMapInnerObserver o = new SwitchMapInnerObserver(this);

            for (;;) {
                SwitchMapInnerObserver current = inner.get();
                if (current == INNER_DISPOSED) {
                    break;
                }
                if (inner.compareAndSet(current, o)) {
                    if (current != null) {
                        current.dispose();
                    }
                    c.subscribe(o);
                    break;
                }
            }
        }

        @Override
        public void onError(Throwable t) {
            if (errors.addThrowable(t)) {
                if (delayErrors) {
                    onComplete();
                } else {
                    disposeInner();
                    Throwable ex = errors.terminate();
                    if (ex != ExceptionHelper.TERMINATED) {
                        downstream.onError(ex);
                    }
                }
            } else {
                RxJavaPlugins.onError(t);
            }
        }

        @Override
        public void onComplete() {
            done = true;
            if (inner.get() == null) {
                Throwable ex = errors.terminate();
                if (ex == null) {
                    downstream.onComplete();
                } else {
                    downstream.onError(ex);
                }
            }
        }

        void disposeInner() {
            SwitchMapInnerObserver o = inner.getAndSet(INNER_DISPOSED);
            if (o != null && o != INNER_DISPOSED) {
                o.dispose();
            }
        }

        @Override
        public void dispose() {
            upstream.dispose();
            disposeInner();
        }

        @Override
        public boolean isDisposed() {
            return inner.get() == INNER_DISPOSED;
        }

        void innerError(SwitchMapInnerObserver sender, Throwable error) {
            if (inner.compareAndSet(sender, null)) {
                if (errors.addThrowable(error)) {
                    if (delayErrors) {
                        if (done) {
                            Throwable ex = errors.terminate();
                            downstream.onError(ex);
                        }
                    } else {
                        dispose();
                        Throwable ex = errors.terminate();
                        if (ex != ExceptionHelper.TERMINATED) {
                            downstream.onError(ex);
                        }
                    }
                    return;
                }
            }
            RxJavaPlugins.onError(error);
        }

        void innerComplete(SwitchMapInnerObserver sender) {
            if (inner.compareAndSet(sender, null)) {
                if (done) {
                    Throwable ex = errors.terminate();
                    if (ex == null) {
                        downstream.onComplete();
                    } else {
                        downstream.onError(ex);
                    }
                }
            }
        }

        static final class SwitchMapInnerObserver extends AtomicReference<Disposable>
        implements CompletableObserver {

            private static final long serialVersionUID = -8003404460084760287L;

            final SwitchMapCompletableObserver<?> parent;

            SwitchMapInnerObserver(SwitchMapCompletableObserver<?> parent) {
                this.parent = parent;
            }

            @Override
            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            @Override
            public void onError(Throwable e) {
                parent.innerError(this, e);
            }

            @Override
            public void onComplete() {
                parent.innerComplete(this);
            }

            void dispose() {
                DisposableHelper.dispose(this);
            }
        }
    }
}