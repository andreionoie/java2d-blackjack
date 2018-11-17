public class Credit implements Comparable<Credit> {
    private double amount;

    public Credit(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Can't have negative value credit.");
        this.amount = amount;
    }

    public Credit() {
        this(0.0);
    }

    public void withdraw(double howMuch) {
        if (howMuch > amount)
            throw new IllegalArgumentException("Can't extract more than account has.");

        amount -= howMuch;
    }

    public void deposit(double howMuch) {
        amount += howMuch;
    }

    public double get() {
        return amount;
    }

    public double empty() {
        double tmp = amount;
        amount = 0;
        return tmp;
    }

    @Override
    public int compareTo(Credit o) {
        if (this.amount > o.get())
            return 1;
        else if (this.amount < o.get())
            return -1;

        return 0;
    }
}
