package bank.benchmarks;

import bank.Bank;
import bank.SuperPool;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@BenchmarkMode(Mode.AverageTime) // нас интересует среднее время
@Warmup(iterations = 5) // несколько итераций будет занимать прогрев
@Measurement(iterations = 50, batchSize = 10) //каждый метод вызовем 20 раз для точности
@OutputTimeUnit(TimeUnit.SECONDS) // время будем считать в миллисекундах
@State (Scope.Benchmark)

public class jmhTest {
    private int threadCount = 10;
    private int transactions = 200;
    private int accountCount = 5000;
    Bank testBank = new Bank();

    public static void main(String... args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @Fork(1)
    public void testStandardPool() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();
        for (int j = 0; j < transactions; j++) {
            futures.add(pool.submit(() -> {
                try {
                    testBank.smartTransfer(String.format("%06d", ThreadLocalRandom.current().nextInt(accountCount)),
                            String.format("%06d", ThreadLocalRandom.current().nextInt(accountCount)),
                            BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(49_005)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        pool.shutdown();  // ждём выполнения всех поставленных задач и отключаемся
    }

    @Benchmark
    @Fork(1)
    public void testMyPool() throws ExecutionException, InterruptedException {
        SuperPool pool = new SuperPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();
        for (int j = 0; j < transactions; j++) {
            futures.add(pool.submit(() -> {
                try {
                    testBank.smartTransfer(String.format("%06d", ThreadLocalRandom.current().nextInt(accountCount)),
                            String.format("%06d", ThreadLocalRandom.current().nextInt(accountCount)),
                            BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(49_005)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        pool.shutdown();  // ждём выполнения всех поставленных задач и отключаемся
    }

//    public static void runCode(Executor pool) {
//
//    }
}

