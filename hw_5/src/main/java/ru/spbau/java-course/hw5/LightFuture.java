package ru.spbau.javacourse.hw5;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.function.Supplier;
import java.util.function.Function;
import java.lang.RuntimeException;

class LightExecutionException extends Exception {
    public LightExecutionException() {
        super("Exception caused in pool's thread");
    }
}

public class LightFuture<R> {
    public LightFuture(ThreadPool pool, Supplier<? extends R> supplier) {
        this.pool = pool;
        this.supplier = supplier;
        result = null;
        hasResult = false;
        failure = false;
        descendent = null;
    }

    public synchronized R get() throws LightExecutionException, InterruptedException {
        Logger.log("Wanna get a result");
        while(!hasResult) {
            if(failure) {
                throw new LightExecutionException();
            }
            Logger.log("Waiting for result...");
            wait();
        }
        Logger.log("Got a result!");
        return result;
    }

    public synchronized boolean isReady() {
        return hasResult;
    }

    public synchronized <RR> LightFuture<RR> thenApply(Function<? super R, RR> f) {
        LightFuture<RR> newTask = new LightFuture<RR>(pool,
                () -> {
                    RR ret = null; 
                    try {
                        ret = f.apply(get());
                    } catch (Exception e) {
                        throw new RuntimeException();
                    } 
                    return ret;
                });
        descendent = newTask;
        if(hasResult || failure) {
            pool.addFuture(newTask);
        }
        return newTask;
    }

    protected void run() {
        R res;
        try {
            Logger.log("Executing a task...");
            res = supplier.get();
            Logger.log("Finish. Try to lock myself and set result.");
            synchronized(this) {
                result = res;
                hasResult = true;
                if(descendent != null) {
                    pool.addFuture(descendent);
                }
            }
        } catch (Throwable t) {
            failure = true;
        } finally {
            synchronized(this) {
                notifyAll();
            }
            pool.notifyThreads();
        }
        Logger.log("Success. Finish.");
    }


    private boolean failure;
    private boolean hasResult;
    private R result;
    private Supplier<? extends R> supplier;
    private LightFuture<?> descendent;
    private ThreadPool pool;
}
