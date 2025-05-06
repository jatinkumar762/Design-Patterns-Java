package app;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String name;
    private Map<String, Double> balances;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.balances = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, Double> balances) {
        this.balances = balances;
    }
}
