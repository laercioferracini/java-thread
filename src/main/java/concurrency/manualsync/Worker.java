package concurrency.manualsync;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 12:46</pre>
 */

class Worker implements Runnable {
    private final BankAccount account;

    public Worker(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            double startBalance = account.getBalance();
            //synchronized blocks provides flexibility
            synchronized (account) {
                account.deposit(10.0);
            }
            double endBalance = account.getBalance();
        }
    }
}


