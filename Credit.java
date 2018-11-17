public class Credit implements Comparable<Credit> {
    private double balance;

    public Credit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Can't have negative value credit.");
        this.balance = amount;
    }

    public Credit() {
        this(0.0);
    }

    public double withdraw(double howMuch) {
        if (howMuch > balance)
            throw new IllegalArgumentException("Can't extract more than account has.");

        balance -= howMuch;
        return howMuch;
    }

    public void deposit(double howMuch) {
        balance += howMuch;
    }

    public void withdraw(Credit howMuch) {
        withdraw(howMuch.get());
    }

    public void deposit(Credit howMuch) {
        deposit(howMuch.get());
    }

    public double get() {
        return balance;
    }

    public double empty() {
        double tmp = balance;
        balance = 0;
        return tmp;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "balance=" + balance +
                '}';
    }

    @Override
    public int compareTo(Credit o) {
        if (this.balance > o.get())
            return 1;
        else if (this.balance < o.get())
            return -1;

        return 0;
    }
}
