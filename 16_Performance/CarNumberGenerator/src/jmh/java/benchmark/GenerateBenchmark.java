package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // нас интересует среднее время
@Warmup(iterations = 5) // несколько итераций будет занимать прогрев
@Measurement(iterations = 20, batchSize = 5) //каждый метод вызовем 5 раз для точности
@OutputTimeUnit(TimeUnit.MILLISECONDS) // время будем считать в миллисекундах
@State(Scope.Benchmark)
public class GenerateBenchmark {


    public static void main(String... args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    List<Integer> regions = new ArrayList<>();

    @Setup
    public void init() {
        for (int i = 1; i < Loader.REGIONS; i++){
            regions.add(i);
        }
    }


    @Benchmark
    @Fork(1)
    public void testOldGenerate() throws IOException {
        Generator.oldGenerate();
    }

    @Benchmark
    @Fork(1)
    public void testNewGenerate() throws IOException {
        Generator.newGenerate(regions, 0);
    }
}
