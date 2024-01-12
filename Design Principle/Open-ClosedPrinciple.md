
* SOFTWARE ENTITIES(Classes, Modules, Methods etc.) SHOULD BE OPEN FOR EXTENSION, BUT CLOSED FOR MODIFICATION
* Open for Extension - Extend existing behavior 
* Closed for Modification - Existing code remains unchanged

```java
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CallHistory {

    public static class Call {
        
        private LocalDateTime begin;
        
        private long duration;

        private Long subscriberId;

        public Call(Long subscriberId, LocalDateTime begin, long duration) {
            this.begin = begin;
            this.duration = duration;
            this.subscriberId = subscriberId;
        }

        /**
         * @return the begin
         */
        public LocalDateTime getBegin() {
            return begin;
        }

        /**
         * @return the duration
         */
        public long getDuration() {
            return duration;
        }

        /**
         * @return the subscriber
         */
        public Long getSubscriberId() {
            return subscriberId;
        }

    }

    private static final Map<Long, List<Call>> CALLS = new HashMap<>();

    public synchronized static List<Call> getCurrentCalls(Long subscriberId) {
        if(!CALLS.containsKey(subscriberId)) {
            return Collections.emptyList();
        }
        return CALLS.get(subscriberId);
    }

    public synchronized static void addSession(Long subscriberId, LocalDateTime begin, long duration) {
        List<Call> calls;
        if(!CALLS.containsKey(subscriberId)) {
            calls = new LinkedList<>();
            CALLS.put(subscriberId, calls);
        } else {
            calls = CALLS.get(subscriberId);
        }
        calls.add(new Call(subscriberId, begin, duration));
    }
}

public class InternetSessionHistory {

    public static class InternetSession {
        
        private LocalDateTime begin;

        private Long subscriberId;

        private Long dataUsed;
        
        public InternetSession(Long subscriberId, LocalDateTime begin, long dataUsed) {
            this.begin = begin;
            this.dataUsed = dataUsed;
            this.subscriberId = subscriberId;
        }

        /**
         * @return the begin
         */
        public LocalDateTime getBegin() {
            return begin;
        }

        /**
         * @return the data used
         */
        public long getDataUsed() {
            return dataUsed;
        }

        /**
         * @return the subscriber
         */
        public Long getSubscriberId() {
            return subscriberId;
        }

    }
    private static final Map<Long, List<InternetSession>> SESSIONS = new HashMap<>();

    public synchronized static List<InternetSession> getCurrentSessions(Long subscriberId) {
        if(!SESSIONS.containsKey(subscriberId)) {
            return Collections.emptyList();
        }
        return SESSIONS.get(subscriberId);
    }

    public synchronized static void addSession(Long subscriberId, LocalDateTime begin, long dataUsed) {
        List<InternetSession> sessions;
        if(!SESSIONS.containsKey(subscriberId)) {
            sessions = new LinkedList<>();
            SESSIONS.put(subscriberId, sessions);
        } else {
            sessions = SESSIONS.get(subscriberId);
        }
        sessions.add(new InternetSession(subscriberId, begin, dataUsed));
    }
}
```

```java
//base class - closed for modification
public abstract class Subscriber {
	
	protected Long subscriberId;

	protected String address;

	protected Long phoneNumber;
	
	protected int baseRate;

	public Long getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(Long subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
	public int getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(int baseRate) {
		this.baseRate = baseRate;
	}

	public abstract double calculateBill(); //extension
    
}


import java.util.List;
public class PhoneSubscriber extends Subscriber {

    //only for demonstration - open for extension
    @Override
    public double calculateBill() {
        List<CallHistory.Call> sessions = CallHistory.getCurrentCalls(subscriberId);
        long totalDuration = sessions.stream().mapToLong(CallHistory.Call::getDuration).sum();
        return totalDuration*baseRate/100;
    }

}


import java.util.List;
public class ISPSubscriber extends Subscriber {

    private long freeUsage;

    //only for demonstration
    @Override
    public double calculateBill() {
        List<InternetSessionHistory.InternetSession> sessions = InternetSessionHistory.getCurrentSessions(subscriberId);
        long totalData = sessions.stream().mapToLong(InternetSessionHistory.InternetSession::getDataUsed).sum();
        long chargeableData = totalData - freeUsage;
        
        if(chargeableData <= 0) {
        	return 0;
        }
        return chargeableData*baseRate/100;
    }

    /**
     * @return the freeUsage
     */
    public long getFreeUsage() {
        return freeUsage;
    }

    /**
     * @param freeUsage the freeUsage to set
     */
    public void setFreeUsage(long freeUsage) {
        this.freeUsage = freeUsage;
    }
    
}
```

#### Reference
* [https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects](https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects)
