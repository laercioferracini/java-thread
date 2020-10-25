package concurrency;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 13:13</pre>
 */
@DisplayName("Worker test should")
class WorkerTest {

    private ExecutorService es;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        es = Executors.newFixedThreadPool(5);
        account = new BankAccount(100);
    }

    @Test
    @DisplayName("checking BankAccount One Thread ok")
    void checkingBankAccountOneThread() throws InterruptedException {
        es = Executors.newFixedThreadPool(5);
        account = new BankAccount(100);

        Worker worker = new Worker(account);
        es.submit(worker);

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
        Assertions.assertEquals(200, account.getBalance());
    }

    @Test
    @DisplayName("checking BankAccount multi Thread ok")
    void checkingBankAccountMultiThread() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            Worker worker = new Worker(account);
            es.submit(worker);
        }
        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
        Assertions.assertEquals(5100, account.getBalance());
    }
}