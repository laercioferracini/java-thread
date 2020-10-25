package concurrency;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 12:46</pre>
 */

public class Worker implements Runnable{
    private BankAccount account;

    public Worker(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            double startBalance = account.getBalance();
            account.deposit(10.0);
            double endBalance = account.getBalance();
        }
    }
}


