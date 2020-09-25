import bank.Bank;
import bank.SuperPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static junit.framework.TestCase.assertEquals;

public class BankTest {

    private Bank testBank;
    private Random testRandom;
    private final int threadCount = 5;
    private final int transactions = 10_000_000;
    private Logger logger = LogManager.getLogger(BankTest.class);

    @Before
    public void setUp() {
        testBank = new Bank();
        testRandom = new Random();
    }

    @Test
    public void testTransfer() throws InterruptedException, ExecutionException {

        BigDecimal before = testBank.getSumBalance();
        int accountCount = 500;
        //  ExecutorService pool = Executors.newFixedThreadPool(threadCount);
         SuperPool pool = new SuperPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();
        for (int j = 0; j < transactions; j++) {
            futures.add(pool.submit(() ->  {
                try {
                    testBank.smartTransfer(String.format("%06d", testRandom.nextInt(accountCount)),
                            String.format("%06d", testRandom.nextInt(accountCount)),
                            BigDecimal.valueOf(testRandom.nextInt(50_005)));
                } catch (InterruptedException e) {
                    logger.error(Thread.currentThread() + " " + e.getMessage());
                }
             //   return null;
            }));
        }
        for (Future<?> future : futures) {
            future.get();
        }
        pool.shutdown();  // ждём выполнения всех поставленных задач и отключаемся



        BigDecimal after = testBank.getSumBalance();
        assertEquals(before, after);
    }

    @After
    public void tearDown() {

    }
}
