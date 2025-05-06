package app.split;

import app.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentSplitStrategy implements SplitStrategy {

    public boolean validate(List<Split> splits, double totalAmount) {
        double percentSum = 0;
        for (Split s : splits) percentSum += ((PercentSplit) s).getPercentage();
        return Math.abs(percentSum - 100.0) < 0.01;
    }

    public Map<User, Double> calculateShare(List<Split> splits, double totalAmount) {

        Map<User, Double> map = new HashMap<>();
        for (Split split : splits) {
            PercentSplit ps = (PercentSplit) split;
            double share = totalAmount * ps.getPercentage() / 100.0;
            split.setAmount(share);
            map.put(ps.getUser(), share);
        }
        return map;
    }
}
