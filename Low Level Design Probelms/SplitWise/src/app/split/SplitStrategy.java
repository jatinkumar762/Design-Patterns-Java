package app.split;

import app.User;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    boolean validate(List<Split> splits, double totalAmount);

    Map<User, Double> calculateShare(List<Split> splits, double totalAmount);
}
