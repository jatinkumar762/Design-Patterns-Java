package app.split;

import app.User;

abstract public class Split {
    private User user;
    private double amount;

    public Split(User user) {
        this.user = user;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
