package app.split;

import app.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public boolean validate(List<Split> splits, double totalAmount) {
        double sum = 0;
        for (Split s : splits) sum += s.getAmount();
        return Math.abs(sum - totalAmount) < 0.01;
    }

    @Override
    public Map<User, Double> calculateShare(List<Split> splits, double totalAmount) {
        Map<User, Double> map = new HashMap<>();
        for (Split split : splits) map.put(split.getUser(), split.getAmount());
        return map;
    }
}
