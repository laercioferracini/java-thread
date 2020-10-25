package concurrency.manualsync;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 17:08</pre>
 */

class TxPromoWorkerTest {

    @DisplayName(value = "deposit bonus in thred safe")
    @RepeatedTest(value = 300)
    void depositBonusAndWithdrawal() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(300);
        TxPromoWorker[] workers = getWorkers(account);

        for (TxWorker worker : workers) {
            es.submit(worker);
        }

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
        var endBalance = account.getBalance();
        assertEquals(410, endBalance);

    }

    private static TxPromoWorker[] getWorkers(BankAccount account) {
        var workers = new TxPromoWorker[4];

        workers[0] = new TxPromoWorker(account, 'd', 100);
        workers[1] = new TxPromoWorker(account, 'd', 100);
        workers[2] = new TxPromoWorker(account, 'd', 100);
        workers[3] = new TxPromoWorker(account, 'w', 200);

        return workers;
    }
}