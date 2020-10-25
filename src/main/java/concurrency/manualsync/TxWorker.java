package concurrency.manualsync;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 12:46</pre>
 */

class TxWorker implements Runnable {
    protected final BankAccount account;
    protected char txType; //'w' -> withdrawal, 'd' -> deposit
    protected double amount;

    public TxWorker(BankAccount account, char txType, double amount) {
        this.account = account;
        this.txType = txType;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println("Tread in: " + Thread.currentThread().getName());
        if (txType == 'w')
            account.withdrawal(amount);
        else if (txType == 'd')
            account.deposit(amount);
    }
}


