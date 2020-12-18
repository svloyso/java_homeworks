package ru.spbau.javacourse.hw5;

import java.util.function.Supplier;
import java.util.function.Function;

import org.junit.Test;
import static org.junit.Assert.*;

public class ThreadPoolTest 
{
    class SleepTask implements Supplier<Void> {
        public SleepTask(long howLong) {
            this.howLong = howLong;
        }
        public Void get() {
            try {
                Thread.sleep(howLong);
            } catch (InterruptedException e) {
                /*EMPTY*/
            } 
            return null;
        }
        private long howLong;
    }
    @Test(timeout=3100)
    public void testAtLeastNThreadsInPool() throws InterruptedException, LightExecutionException {
        System.out.println("testAtLeastNThreadsInPool");
        ThreadPool pool = new ThreadPool(3);
        LightFuture<Void> lf1 = pool.addTask(new SleepTask(3000));
        LightFuture<Void> lf2 = pool.addTask(new SleepTask(3000));
        LightFuture<Void> lf3 = pool.addTask(new SleepTask(3000));
        assertFalse(lf1.isReady());
        assertFalse(lf2.isReady());
        assertFalse(lf3.isReady());
        lf1.get();
        lf2.get();
        lf3.get();
        assertTrue(lf1.isReady());
        assertTrue(lf2.isReady());
        assertTrue(lf3.isReady());
        pool.shutdown();
    }

    @Test(timeout=5100)
    public void testThenApply() throws InterruptedException, LightExecutionException {
        System.out.println("testThenApply");
        ThreadPool pool = new ThreadPool(3);
        long timeStampBefore = System.currentTimeMillis();
        LightFuture<Integer> firstTask = pool.addTask(new Supplier<Integer>() {
            public Integer get() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    /*EMPTY*/
                } 
                return 21;
            }
        });
        LightFuture<Integer> secondTask = firstTask.thenApply(new Function<Integer, Integer>() {
            public Integer apply(Integer x) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    /*EMPTY*/
                }
                return x + 21;
            }
        });
        int res = secondTask.get();
        long timeStampAfter = System.currentTimeMillis();
        assertEquals(42, res);
        assertTrue(timeStampAfter - timeStampBefore > 5000);
        pool.shutdown();
    }

    @Test(expected=LightExecutionException.class, timeout=1000)
    public void testLightFutureException() throws LightExecutionException, InterruptedException {
        System.out.println("testLightFutureException");
        ThreadPool pool = new ThreadPool(2);
        LightFuture<Integer> task1 = pool.addTask(() -> 42);
        LightFuture<Integer> task2 = pool.addTask(() -> 1/0);
        int res1 = task1.get();
        assertEquals(42, res1);
        task2.get();
        pool.shutdown();
    }
}
