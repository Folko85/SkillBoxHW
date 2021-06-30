package benchmark;

import java.util.ArrayList;
import java.util.List;

public class Loader {

    public static final int REGIONS = 10;

    public static void main(String[] args) throws Exception {

        List<Integer> regions = new ArrayList<>();
        for (int i = 1; i < REGIONS; i++) {
            regions.add(i);
        }
        long start = System.currentTimeMillis();
        Generator.oldGenerate();
        System.out.println((System.currentTimeMillis() - start) + " ms");
        start = System.currentTimeMillis();
        /**
         * для многопоточки взял логику из задания 13.1 (11.1 по старому) так как она почти подходит и почему бы и нет
         */
        Generator.newGenerate(regions, 9);
        System.out.println((System.currentTimeMillis() - start) + " ms");
        System.out.println("Теперь проверим многопоточность");
        start = System.currentTimeMillis();
        int processorsCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Ядер задействовано: " + processorsCount);

        int regionsPerThread = regions.size() / processorsCount;  // количество регионов на поток
        int oneMoreSizeCount = regions.size() % processorsCount;  // количество потоков, где на 1 регион больше
        int bound = oneMoreSizeCount * (regionsPerThread + 1);
        List<Integer> subListOneMore = regions.subList(0, bound); // здесь все регионы для увеличенных потоков
        List<Integer> subListStandard = regions.subList(bound, regions.size());
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < processorsCount; i++) {
            int lowBound = (i < oneMoreSizeCount) ? i * (regionsPerThread + 1) : (i - oneMoreSizeCount) * regionsPerThread;
            int upBound = (i < oneMoreSizeCount) ? lowBound + regionsPerThread + 1 : lowBound + regionsPerThread;
            List<Integer> perThread = (i < oneMoreSizeCount) ? subListOneMore.subList(lowBound, upBound) : subListStandard.subList(lowBound, upBound);

            Generator generator = new Generator(perThread, i);
            Thread thread = new Thread(generator);

            thread.start();
            threads.add(thread);
        }
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }


}
