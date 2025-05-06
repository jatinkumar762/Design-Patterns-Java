package app;

import app.split.Split;
import app.split.SplitType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Expense {
    private String id;
    private double amount;
    private String description;
    private User paidBy;
    private List<Split> splits;
    private SplitType splitType;

    public Expense(double amount, String description, User paidBy, List<Split> splits, SplitType type) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.description = description;
        this.paidBy = paidBy;
        this.splits = splits;
        this.splitType = type;
    }

    public void addSplit(Split split){
        this.splits.add(split);
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }
}
