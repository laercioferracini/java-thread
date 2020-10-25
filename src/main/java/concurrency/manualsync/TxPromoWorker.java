package concurrency.manualsync;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 16:52</pre>
 */

public class TxPromoWorker extends TxWorker {

    public TxPromoWorker(BankAccount account, char txType, double amount) {
        super(account, txType, amount);
    }

    @Override
    public void run() {

        if (txType == 'w')
            account.withdrawal(amount);
        else if (txType == 'd') {
            synchronized (account) {
                System.out.println("Tread in: " + Thread.currentThread().getName());
                account.deposit(amount);
                if (account.getBalance() > 500) {
                    double bonus = (account.getBalance() - 500) * 0.1;
                    account.deposit(bonus);
                }
            }
        }
    }
}
