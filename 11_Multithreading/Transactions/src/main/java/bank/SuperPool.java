package bank;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SuperPool implements Executor {
    private final BlockingDeque<Runnable> runQueue = new LinkedBlockingDeque<>();
    private volatile boolean isShutdown = false;
    private volatile AtomicInteger threadCounter;
    private int nThread;

    public SuperPool(int nThreads) {
        this.threadCounter = new AtomicInteger();
        this.nThread = nThreads;
    }

    @Override
    public void execute(Runnable command) {
        if(threadCounter.get() < nThread){
            new Worker().start();
            threadCounter.getAndIncrement();
        }
        if (!isShutdown) {
            runQueue.offer(command);
        }
    }


    public void shutdown() {
        isShutdown = true;
    }


    public Future<?> submit(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException();
        } else {
            RunnableFuture<Void> task = new FutureTask(runnable, null);
            this.execute(task);
            return task;
        }
    }


    public <T> Future<T> submit(Runnable runnable, T result) {
        if (runnable == null) {
            throw new IllegalArgumentException();
        } else {
            RunnableFuture<T> task = new FutureTask<T>(runnable, result);
            this.execute(task);
            return task;
        }
    }


    public <T> Future<T> submit(Callable<T> callable) {
        if (callable == null) {
            throw new IllegalArgumentException();
        } else {
            RunnableFuture<T> task = new FutureTask<T>(callable);
            this.execute(task);
            return task;
        }
    }

    private final class Worker extends Thread {

        @Override
        public void run() {
            while (!isShutdown) {
                Runnable nextTask = runQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }
}