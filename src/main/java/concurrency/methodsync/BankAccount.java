package concurrency.methodsync;

/**
 * @author lferracini
 * @project = java-thread
 * @since <pre>25/10/2020 12:44</pre>
 */

public class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void deposit(double amount){
        balance += amount;
    }
}
