#### Introduction
* We need to avoid coupling the code which sends request to the code which handles that request.
* Typically the code which wants some requested calls the exact method on an exact object to process it,thus the tight coupling. Chain of responsibility solves this problem by giving more than one object, chance to process the request.
* We create objects which are chained together by one object knowing reference of object which is next in chain.We give request to first object in chain, if it can't handle that it simply passes the request down the chain.

#### Implementation Steps
* We start by defining handler interface/abstract class 
  * Handler must define a method to accept incoming request 
  * Handler can define method to access successor in chain. If it's an abstract class then we can even maintain successor.
* Next we implement handler in one or more concrete handlers.Concrete handler should check if it can handle the request. If not then it should pass request to next handler.
* We have to create our chain of objects next. We can do it in client. Typically in real world this job will be done by some framework or initialization code written by you.
* Client needs to know only the first object in chain. It'll pass on request to this object

#### Implementation

```java
package behavioral.chainofresponsiblity;

import java.time.LocalDate;
import java.time.Period;

//Represents a request in our chain of responsibility

public class LeaveApplication {
	public enum Type {
		Sick, PTO, LOP
	};

	public enum Status {
		Pending, Approved, Rejecetd
	};

	private Type type;

	private LocalDate from;

	private LocalDate to;

	private String processedBy;

	private Status status;

	public LeaveApplication(Type type, LocalDate from, LocalDate to) {
		this.type = type;
		this.from = from;
		this.to = to;
		this.status = Status.Pending;
	}

	public Type getType() {
		return type;
	}

	public LocalDate getFrom() {
		return from;
	}

	public LocalDate getTo() {
		return to;
	}

	public int getNoOfDays() {
		return Period.between(from, to).getDays();
	}

	public String getProcessedBy() {
		return processedBy;
	}

	public Status getStatus() {
		return status;
	}

	public void approve(String approverName) {
		this.status = Status.Approved;
		this.processedBy = approverName;
	}

	public void reject(String approverName) {
		this.status = Status.Rejecetd;
		this.processedBy = approverName;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	@Override
	public String toString() {
		return type + " leave for " + getNoOfDays() + " day(s) " + status + " by " + processedBy;
	}

	public static class Builder {
		private Type type;
		private LocalDate from;
		private LocalDate to;
		private LeaveApplication application;

		private Builder() {

		}

		public Builder withType(Type type) {
			this.type = type;
			return this;
		}

		public Builder from(LocalDate from) {
			this.from = from;
			return this;
		}

		public Builder to(LocalDate to) {
			this.to = to;
			return this;
		}

		public LeaveApplication build() {
			this.application = new LeaveApplication(type, from, to);
			return this.application;
		}

		public LeaveApplication getApplication() {
			return application;
		}
	}
}
```

```java
//This represents a handler in chain of responsibility
public interface LeaveApprover {
	void processLeaveApplication(LeaveApplication application);	
	String getApproverRole();
}

//Abstract handler
public abstract class Employee implements LeaveApprover {
	private String role;

	private LeaveApprover successor;

	public Employee(String role, LeaveApprover successor) {
		this.role = role;
		this.successor = successor;
	}

	@Override
	public void processLeaveApplication(LeaveApplication application) {
		if (!processRequest(application) && successor != null) {
			successor.processLeaveApplication(application);
		}
	}

	protected abstract boolean processRequest(LeaveApplication application);

	@Override
	public String getApproverRole() {
		return role;
	}
}

//A concrete handler
public class ProjectLead extends Employee {
	public ProjectLead(LeaveApprover successor) {
		super("Project Lead", successor);
	}

	@Override
	protected boolean processRequest(LeaveApplication application) {
		// type is sick leave & duration is less than or equal to 2 days
		if (application.getType() == LeaveApplication.Type.Sick) {
			if (application.getNoOfDays() <= 2) {
				application.approve(getApproverRole());
				return true;
			}
		}
		return false;
	}
}

package behavioral.chainofresponsiblity;

public class Manager extends Employee {
	public Manager(LeaveApprover nextApprover) {
		super("Manager", nextApprover);
	}

	@Override
	protected boolean processRequest(LeaveApplication application) {
		switch (application.getType()) {
		case Sick:
			application.approve(getApproverRole());
			return true;
		case PTO:
			if (application.getNoOfDays() <= 5) {
				application.approve(getApproverRole());
				return true;
			}
		}
		return false;
	}
}

package behavioral.chainofresponsiblity;

import behavioral.chainofresponsiblity.LeaveApplication.Type;

public class Director extends Employee {
	public Director(LeaveApprover nextApprover) {
		super("Director", nextApprover);
	}

	@Override
	protected boolean processRequest(LeaveApplication application) {
		if (application.getType() == Type.PTO) {
			application.approve(getApproverRole());
			return true;
		}
		return false;
	}
}
```

```java
package behavioral.chainofresponsiblity;

import java.time.LocalDate;

import behavioral.chainofresponsiblity.LeaveApplication.Type;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeaveApplication application = LeaveApplication.getBuilder().withType(Type.Sick).from(LocalDate.now())
				.to(LocalDate.of(2018, 2, 28)).build();
		System.out.println(application);
		System.out.println("**************************************************");
		LeaveApprover approver = createChain();
		approver.processLeaveApplication(application);
		System.out.println(application);
	}

	private static LeaveApprover createChain() {
		Director director = new Director(null);
		Manager manager = new Manager(director);
		ProjectLead lead = new ProjectLead(manager);
		return lead;
	}
}

```

#### Implementation Considerations
* Prefer defining handler as interface as it allows you to implement chain of responsibility without worrying about single inheritance rule of Java.
* Handlers can allow the request to propagate even if they handle the request. 

#### Design Considerations
* Sometimes you can think of using existing connections or chains in objects. For example if you are using composite pattern you already have a chain which can be used to implement this behavior.

#### Example
* Probably the best example of chain of responsibility is servlet filters. Each filter gets a chance to handle incoming request and passes it down the chain once its work is done.

#### Drawbacks
* There is no guarantee provided in the pattern that a request will be handled. Request can traverse whole chain and fall off at the other end without ever being processed and we won't know it. 
* It is easy to misconfigure the chain when we are connecting successors.There is nothing in the pattern that will let us know of any such problems. Some handlers may be left unconnected to chain.


### References
* https://www.geeksforgeeks.org/chain-responsibility-design-pattern/
* https://howtodoinjava.com/design-patterns/behavioral/chain-of-responsibility-design-pattern/


