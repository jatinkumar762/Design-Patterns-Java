package app;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<User> users;
    private List<Expense> expenses;
    private User createdBy;

    public Group(String id, String name, User createdBy) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.createdBy = createdBy;

        this.users.add(createdBy);
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void addExpense(Expense expenses) {
        this.expenses.add(expenses);
    }
}
