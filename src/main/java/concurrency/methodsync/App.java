package concurrency.methodsync;

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
        for (int ix = 0; ix < 100; ix++) {
            ExecutorService es = Executors.newFixedThreadPool(5);
            BankAccount account = new BankAccount(100);
            var startBalance = account.getBalance();
            for (int i = 0; i < 5; i++) {
                Worker worker = new Worker(account);
                es.submit(worker);
            }

            es.shutdown();
            es.awaitTermination(60, TimeUnit.SECONDS);
            var endBalance = account.getBalance();

            System.out.printf("end Balance:%s Start Balance: %s in %s%n", endBalance,startBalance, Thread.currentThread().getName());
        }
    }
}
