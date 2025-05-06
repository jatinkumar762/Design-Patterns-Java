package app.split;

import app.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public boolean validate(List<Split> splits, double totalAmount) {
        return true;
    }

    @Override
    public Map<User, Double> calculateShare(List<Split> splits, double totalAmount) {

        Map<User, Double> map = new HashMap<>();
        double share = totalAmount / splits.size();

        for (Split split : splits) {
            split.setAmount(share);
            map.put(split.getUser(), share);
        }

        return map;
    }
}
