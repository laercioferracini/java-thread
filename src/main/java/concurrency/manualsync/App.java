package concurrency.manualsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 12:51</pre>
 */

public class App {
    public static void main(String[] args) throws InterruptedException {
        //sync();
        manualSync();
    }

    private static void sync() throws InterruptedException {
        for (int ix = 0; ix < 100; ix++) {
            ExecutorService es = Executors.newFixedThreadPool(5);
            BankAccount account = new BankAccount(200);
            var startBalance = account.getBalance();
            for (int i = 0; i < 5; i++) {
                Worker worker = new Worker(account);
                es.submit(worker);
            }

            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
            var endBalance = account.getBalance();

            System.out.printf("end Balance:%s Start Balance: %s in %s%n", endBalance, startBalance, Thread.currentThread().getName());
        }
    }

    private static void manualSync() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(300);
        TxWorker[] workers = getWorkers(account);

        var startBalance = account.getBalance();
        for (TxWorker worker : workers) {
            es.submit(worker);
        }

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
        var endBalance = account.getBalance();

        System.out.printf("end Balance:%s Start Balance: %s in %s%n", endBalance, startBalance, Thread.currentThread().getName());
    }

    private static TxWorker[] getWorkers(BankAccount account) {
        var workers = new TxPromoWorker[5];

        workers[0] = new TxPromoWorker(account, 'd', 100);
        workers[1] = new TxPromoWorker(account, 'd', 100);
        workers[2] = new TxPromoWorker(account, 'd', 100);
        workers[3] = new TxPromoWorker(account, 'w', 100);
        workers[4] = new TxPromoWorker(account, 'w', 100);

        return workers;
    }

}
