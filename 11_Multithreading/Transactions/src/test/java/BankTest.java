import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

public class BankTest {

    private Bank testBank;
    private Random testRandom;
    private final int threadCount = 10;
    private Logger logger = LogManager.getLogger(BankTest.class);

    @Before
    public void setUp() {
        testBank = new Bank();
        testRandom = new Random();
    }

    @Test
    public void testTransfer() throws InterruptedException {

        BigDecimal before = testBank.getSumBalance();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    try {
                        testBank.transfer(String.format("%06d", testRandom.nextInt(100)),
                                String.format("%06d", testRandom.nextInt(100)),
                                BigDecimal.valueOf(testRandom.nextInt(55_000)));
                    } catch (InterruptedException e) {
                        logger.error(Thread.currentThread() + " " + e.getMessage());
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        BigDecimal after = testBank.getSumBalance();
        assertEquals(before, after);
    }

    @After
    public void tearDown() {

    }
}
