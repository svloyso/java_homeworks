package ru.spbau.javacourse.hw5;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.function.Supplier;

class Logger {
    static public void log(String message) {
        System.out.println(Thread.currentThread().getName() + " -- " + message);
    }
}


public class ThreadPool {
    class ThreadFunc implements Runnable {
        public ThreadFunc() {
            tasks = ThreadPool.this.tasks;
        }
        public void run() {
            try {
                while(true) {
                    LightFuture<?> task = null;
                    synchronized(ThreadPool.this) {
                        while(tasks.size() == 0) {
                            Logger.log("Waiting for another task");
                            ThreadPool.this.wait();
                        }
                        task = tasks.poll();
                    }
                    Logger.log("Got a task.");
                    task.run();
                }
            } catch (InterruptedException e) {
                /* EMPTY */
            }
            Logger.log("Finish. Good Bye!");
        }
        private Queue<LightFuture<?>> tasks;
    }

    public ThreadPool(int n) {
        tasks = new LinkedList<>();
        threads = Stream.generate(() -> new Thread(new ThreadFunc()))
                        .limit(n)
                        .peek(t -> t.start())
                        .collect(Collectors.toList());
    }

    public synchronized <R> LightFuture<R> addTask(Supplier<? extends R> s) {
        LightFuture<R> fut = new LightFuture<>(this, s);
        tasks.add(fut);
        notify();
        return fut;
    }

    protected synchronized <R> void addFuture(LightFuture<R> fut) {
        tasks.add(fut);
        notify();
    }

    protected synchronized void notifyThreads() {
        notify();
    }
    
    public void shutdown() {
        Logger.log("Shutting down...");
        threads.stream().forEach(t -> {
            try {
                t.interrupt();
                t.join();
            } catch (InterruptedException e) {/*EMPTY*/}
        });
    }

    private List<Thread> threads;
    private Queue<LightFuture<?>> tasks;
}
