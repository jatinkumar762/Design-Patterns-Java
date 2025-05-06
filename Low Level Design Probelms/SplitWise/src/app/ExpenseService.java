package app;

import app.split.Split;
import app.split.SplitStrategy;
import app.split.SplitType;

import java.util.List;
import java.util.Map;

public class ExpenseService {

    BalanceService balanceService;

    public ExpenseService(){
        this.balanceService = BalanceService.getInstance();
    }

    public void addExpense(Group group, String description, double amount, User paidBy, List<Split> splits, SplitType type) {
        SplitStrategy strategy = SplitStrategyFactory.getStrategy(type);

        if (!strategy.validate(splits, amount)) throw new IllegalArgumentException("Invalid split");

        Map<User, Double> share = strategy.calculateShare(splits, amount);

        Expense expense = new Expense(amount, description, paidBy, splits, type);

        group.getExpenses().add(expense);

        balanceService.updateBalances(group, paidBy, share);
    }

}
