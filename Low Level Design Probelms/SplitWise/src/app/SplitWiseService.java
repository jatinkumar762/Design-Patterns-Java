package app;

import java.util.HashMap;
import java.util.Map;

public class SplitWiseService {

    private static SplitWiseService instance  = null;
    private Map<String, User> userMap;
    private Map<String, Group> groupMap;

    private SplitWiseService(){
        userMap = new HashMap<>();
        groupMap = new HashMap<>();
    }

    public static SplitWiseService getInstance(){

        synchronized (SplitWiseService.class) {
            if (instance == null) {
                instance = new SplitWiseService();
            }
        }

        return instance;
    }

    public void addUser(User user){
        userMap.put(user.getId(), user);
    }

    public void addGroup(Group group){
        groupMap.put(group.getId(), group);
    }
}
