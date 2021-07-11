package benchmark;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static benchmark.Loader.REGIONS;

@BenchmarkMode(Mode.AverageTime) // нас интересует среднее время
@Warmup(iterations = 5) // несколько итераций будет занимать прогрев
@Measurement(iterations = 20, batchSize = 5) //каждый метод вызовем 5 раз для точности
@OutputTimeUnit(TimeUnit.MILLISECONDS) // время будем считать в миллисекундах
@State(Scope.Benchmark)
public class GenerateBenchmark {


    public static void main(String... args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    @Fork(1)
    public void testOldGenerate() throws IOException {
        Generator.oldGenerate();
    }

    @Benchmark
    @Fork(1)
    public void testNewGenerate() {
        for (int i = 1; i < REGIONS; i++) {
            Generator.newGenerate(i);
        }
    }
}
