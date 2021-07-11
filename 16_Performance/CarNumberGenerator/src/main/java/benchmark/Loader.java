package benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Loader {

    public static final int REGIONS = 10;

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();
        Generator.oldGenerate();
        System.out.println((System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
         // разница между старым и новым методами настолько большая, что даже генерируя в 10 раз больше номеров мы в несколько раз быстрее
        for (int i = 1; i < REGIONS; i++) {
            Generator.newGenerate(i);
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");

        System.out.println("Теперь проверим многопоточность");
        start = System.currentTimeMillis();
        int threadCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Ядер задействовано: " + threadCount);

        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 11; i < 10 + REGIONS; i++) {
            int region = i;
            futures.add(pool.submit(() -> Generator.newGenerate(region)));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        pool.shutdown();  // ждём выполнения всех поставленных задач и отключаемся
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }
}
