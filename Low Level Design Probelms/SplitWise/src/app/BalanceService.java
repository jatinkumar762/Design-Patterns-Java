package app;

import java.util.HashMap;
import java.util.Map;

public class BalanceService {

    private static final BalanceService instance = new BalanceService();

    // Map<groupId, Map<fromUserId, Map<toUserId, amount>>>
    private final Map<String, Map<String, Map<String, Double>>> groupBalances = new HashMap<>();

    private BalanceService() {}

    public static BalanceService getInstance() {
        return instance;
    }

    public void updateBalances(Group group, User paidBy, Map<User, Double> shares) {
        String groupId = group.getId();
        groupBalances.putIfAbsent(groupId, new HashMap<>());

        for (Map.Entry<User, Double> entry : shares.entrySet()) {
            User user = entry.getKey();
            double amount = entry.getValue();
            if (user.getId().equals(paidBy.getId())) continue;

            groupBalances.get(groupId).putIfAbsent(user.getId(), new HashMap<>());
            groupBalances.get(groupId).putIfAbsent(paidBy.getId(), new HashMap<>());

            // Update balances
            groupBalances.get(groupId).get(user.getId())
                    .put(paidBy.getId(), groupBalances.get(groupId).get(user.getId()).getOrDefault(paidBy.getId(), 0.0) + amount);

            groupBalances.get(groupId).get(paidBy.getId())
                    .put(user.getId(), groupBalances.get(groupId).get(paidBy.getId()).getOrDefault(user.getId(), 0.0) - amount);
        }
    }

    public void showGroupBalances(Group group) {
        String groupId = group.getId();
        if (!groupBalances.containsKey(groupId)) {
            System.out.println("No balances for group: " + groupId);
            return;
        }

        for (String from : groupBalances.get(groupId).keySet()) {
            for (String to : groupBalances.get(groupId).get(from).keySet()) {
                double amount = groupBalances.get(groupId).get(from).get(to);
                if (amount > 0) {
                    System.out.println(from + " owes " + to + " in group " + group.getName() + ": " + amount);
                }
            }
        }
    }

    public void settleUp(Group group, User from, User to, double amount) {
        String groupId = group.getId();
        Map<String, Map<String, Double>> groupMap = groupBalances.get(groupId);
        if (groupMap == null || !groupMap.containsKey(from.getId()) || !groupMap.get(from.getId()).containsKey(to.getId())) {
            System.out.println("No balance to settle.");
            return;
        }

        double current = groupMap.get(from.getId()).get(to.getId());
        if (amount > current) {
            System.out.println("Amount exceeds current balance.");
            return;
        }

        groupMap.get(from.getId()).put(to.getId(), current - amount);
        groupMap.get(to.getId()).put(from.getId(), groupMap.get(to.getId()).get(from.getId()) + amount);

        System.out.println("Settled " + amount + " from " + from.getName() + " to " + to.getName() + " in group " + group.getName());
    }
}
