
* SOFTWARE ENTITIES(Classes, Modules, Methods etc.) SHOULD BE OPEN FOR EXTENSION, BUT CLOSED FOR MODIFICATION
* Open for Extension Extend existing behavior 
* Closed for Modification Existing code remains unchanged

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