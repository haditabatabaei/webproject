

package io.reactivex.internal.schedulers;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.*;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;


public final class TrampolineScheduler extends Scheduler {
    private static final TrampolineScheduler INSTANCE = new TrampolineScheduler();

    public static TrampolineScheduler instance() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public Worker createWorker() {
        return new TrampolineWorker();
    }

    TrampolineScheduler() {
    }

    @NonNull
    @Override
    public Disposable scheduleDirect(@NonNull Runnable run) {
        RxJavaPlugins.onSchedule(run).run();
        return EmptyDisposable.INSTANCE;
    }

    @NonNull
    @Override
    public Disposable scheduleDirect(@NonNull Runnable run, long delay, TimeUnit unit) {
        try {
            unit.sleep(delay);
            RxJavaPlugins.onSchedule(run).run();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            RxJavaPlugins.onError(ex);
        }
        return EmptyDisposable.INSTANCE;
    }

    static final class TrampolineWorker extends Scheduler.Worker implements Disposable {
        final PriorityBlockingQueue<TimedRunnable> queue = new PriorityBlockingQueue<TimedRunnable>();

        private final AtomicInteger wip = new AtomicInteger();

        final AtomicInteger counter = new AtomicInteger();

        volatile boolean disposed;

        @NonNull
        @Override
        public Disposable schedule(@NonNull Runnable action) {
            return enqueue(action, now(TimeUnit.MILLISECONDS));
        }

        @NonNull
        @Override
        public Disposable schedule(@NonNull Runnable action, long delayTime, @NonNull TimeUnit unit) {
            long execTime = now(TimeUnit.MILLISECONDS) + unit.toMillis(delayTime);

            return enqueue(new SleepingRunnable(action, this, execTime), execTime);
        }

        Disposable enqueue(Runnable action, long execTime) {
            if (disposed) {
                return EmptyDisposable.INSTANCE;
            }
            final TimedRunnable timedRunnable = new TimedRunnable(action, execTime, counter.incrementAndGet());
            queue.add(timedRunnable);

            if (wip.getAndIncrement() == 0) {
                int missed = 1;
                for (;;) {
                    for (;;) {
                        if (disposed) {
                            queue.clear();
                            return EmptyDisposable.INSTANCE;
                        }
                        final TimedRunnable polled = queue.poll();
                        if (polled == null) {
                            break;
                        }
                        if (!polled.disposed) {
                            polled.run.run();
                        }
                    }
                    missed = wip.addAndGet(-missed);
                    if (missed == 0) {
                        break;
                    }
                }

                return EmptyDisposable.INSTANCE;
            } else {
                                return Disposables.fromRunnable(new AppendToQueueTask(timedRunnable));
            }
        }

        @Override
        public void dispose() {
            disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }

        final class AppendToQueueTask implements Runnable {
            final TimedRunnable timedRunnable;

            AppendToQueueTask(TimedRunnable timedRunnable) {
                this.timedRunnable = timedRunnable;
            }

            @Override
            public void run() {
                timedRunnable.disposed = true;
                queue.remove(timedRunnable);
            }
        }
    }

    static final class TimedRunnable implements Comparable<TimedRunnable> {
        final Runnable run;
        final long execTime;
        final int count; 
        volatile boolean disposed;

        TimedRunnable(Runnable run, Long execTime, int count) {
            this.run = run;
            this.execTime = execTime;
            this.count = count;
        }

        @Override
        public int compareTo(TimedRunnable that) {
            int result = ObjectHelper.compare(execTime, that.execTime);
            if (result == 0) {
                return ObjectHelper.compare(count, that.count);
            }
            return result;
        }
    }

    static final class SleepingRunnable implements Runnable {
        private final Runnable run;
        private final TrampolineWorker worker;
        private final long execTime;

        SleepingRunnable(Runnable run, TrampolineWorker worker, long execTime) {
            this.run = run;
            this.worker = worker;
            this.execTime = execTime;
        }

        @Override
        public void run() {
            if (!worker.disposed) {
                long t = worker.now(TimeUnit.MILLISECONDS);
                if (execTime > t) {
                    long delay = execTime - t;
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        RxJavaPlugins.onError(e);
                        return;
                    }
                }

                if (!worker.disposed) {
                    run.run();
                }
            }
        }
    }
}
