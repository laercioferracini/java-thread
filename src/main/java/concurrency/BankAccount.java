package concurrency;

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

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount){
        balance += amount;
    }
}
