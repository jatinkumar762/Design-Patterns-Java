package app;

import app.split.EqualSplit;
import app.split.SplitType;

import java.util.Arrays;

public class SplitWiseApp {

    public static void main(String args[]){

        SplitWiseService splitWiseService = SplitWiseService.getInstance();
        ExpenseService expenseService = new ExpenseService();

        User user1 = new User("1", "Alice");
        User user2 = new User("2", "Bob");
        User user3 = new User("3", "Charlie");

        splitWiseService.addUser(user1);
        splitWiseService.addUser(user2);
        splitWiseService.addUser(user3);


        Group group = new Group("1", "Apartment", user1);
        group.addUser(user2);
        group.addUser(user3);

        splitWiseService.addGroup(group);

        EqualSplit s1 = new EqualSplit(user1);
        EqualSplit s2 = new EqualSplit(user2);

        expenseService.addExpense(group, "Hotel", 1000, user1, Arrays.asList(s1, s2), SplitType.EQUAL);

        BalanceService.getInstance().settleUp(group, user2, user1, 200);

        BalanceService.getInstance().showGroupBalances(group);

    }
}
