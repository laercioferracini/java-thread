package concurrency;

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
        ExecutorService es = Executors.newFixedThreadPool(5);
        BankAccount account = new BankAccount(100);

        Worker worker = new Worker(account);
        es.submit(worker);

        es.shutdown();
        es.awaitTermination(60, TimeUnit.SECONDS);
    }
}
