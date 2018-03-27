

package com.google.common.util.concurrent;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.util.concurrent.Callables.returning;
import static com.google.common.util.concurrent.Futures.getDone;
import static com.google.common.util.concurrent.TestPlatform.verifyThreadWasNotInterrupted;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;


@GwtCompatible(emulated = true)
public class TrustedListenableFutureTaskTest extends TestCase {

  public void testSuccessful() throws Exception {
    TrustedListenableFutureTask<Integer> task = TrustedListenableFutureTask.create(returning(2));
    assertFalse(task.isDone());
    task.run();
    assertTrue(task.isDone());
    assertFalse(task.isCancelled());
    assertEquals(2, getDone(task).intValue());
  }

  public void testCancelled() throws Exception {
    TrustedListenableFutureTask<Integer> task = TrustedListenableFutureTask.create(returning(2));
    assertFalse(task.isDone());
    task.cancel(false);
    assertTrue(task.isDone());
    assertTrue(task.isCancelled());
    assertFalse(task.wasInterrupted());
    try {
      getDone(task);
      fail();
    } catch (CancellationException expected) {
    }
    verifyThreadWasNotInterrupted();
  }

  public void testFailed() throws Exception {
    final Exception e = new Exception();
    TrustedListenableFutureTask<Integer> task =
        TrustedListenableFutureTask.create(
            new Callable<Integer>() {
              @Override
              public Integer call() throws Exception {
                throw e;
              }
            });
    task.run();
    assertTrue(task.isDone());
    assertFalse(task.isCancelled());
    try {
      getDone(task);
      fail();
    } catch (ExecutionException executionException) {
      assertEquals(e, executionException.getCause());
    }
  }

  @GwtIncompatible 
  public void testCancel_interrupted() throws Exception {
    final AtomicBoolean interruptedExceptionThrown = new AtomicBoolean();
    final CountDownLatch enterLatch = new CountDownLatch(1);
    final CountDownLatch exitLatch = new CountDownLatch(1);
    final TrustedListenableFutureTask<Integer> task =
        TrustedListenableFutureTask.create(
            new Callable<Integer>() {
              @Override
              public Integer call() throws Exception {
                enterLatch.countDown();
                try {
                  new CountDownLatch(1).await();                   throw new AssertionError();
                } catch (InterruptedException e) {
                  interruptedExceptionThrown.set(true);
                  throw e;
                } finally {
                }
              }
            });
    assertFalse(task.isDone());
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  task.run();
                } finally {
                  exitLatch.countDown();
                }
              }
            });
    thread.start();
    enterLatch.await();
    assertFalse(task.isDone());
    task.cancel(true);
    assertTrue(task.isDone());
    assertTrue(task.isCancelled());
    assertTrue(task.wasInterrupted());
    try {
      task.get();
      fail();
    } catch (CancellationException expected) {
    }
    exitLatch.await();
    assertTrue(interruptedExceptionThrown.get());
  }

  @GwtIncompatible 
  public void testRunIdempotency() throws Exception {
    final int numThreads = 10;
    final ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    for (int i = 0; i < 1000; i++) {
      final AtomicInteger counter = new AtomicInteger();
      final TrustedListenableFutureTask<Integer> task =
          TrustedListenableFutureTask.create(
              new Callable<Integer>() {
                @Override
                public Integer call() {
                  return counter.incrementAndGet();
                }
              });
      final CyclicBarrier barrier = new CyclicBarrier(numThreads + 1);
      Runnable wrapper =
          new Runnable() {
            @Override
            public void run() {
              awaitUnchecked(barrier);
              task.run();
              awaitUnchecked(barrier);
            }
          };
      for (int j = 0; j < 10; j++) {
        executor.execute(wrapper);
      }
      barrier.await();       barrier.await();       assertEquals(1, task.get().intValue());
      assertEquals(1, counter.get());
    }
    executor.shutdown();
  }

  @GwtIncompatible 
  public void testToString() throws Exception {
    final CountDownLatch enterLatch = new CountDownLatch(1);
    final CountDownLatch exitLatch = new CountDownLatch(1);
    final TrustedListenableFutureTask<Void> task =
        TrustedListenableFutureTask.create(
            new Callable<Void>() {
              @Override
              public Void call() throws Exception {
                enterLatch.countDown();
                new CountDownLatch(1).await();                 return null;
              }
            });
    assertFalse(task.isDone());
    Thread thread =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  task.run();
                } finally {
                  exitLatch.countDown();
                }
              }
            },
            "Custom thread name");
    thread.start();
    enterLatch.await();
    assertFalse(task.isDone());
    String result = task.toString();
    assertThat(result).contains("Custom thread name");
    task.cancel(true);
    exitLatch.await();
  }

  @GwtIncompatible   private void awaitUnchecked(CyclicBarrier barrier) {
    try {
      barrier.await();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}