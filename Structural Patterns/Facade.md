

#### Introduction

* facade (a false or misleading appearance)

* Client has to interact with a large number of interfaces and classes in a subsystem to get result. So client get tightly coupled with those interfaces and classes. Facade solves this problem.

* Client interacts with just the facade now to get the same result. 

#### Implementation Consideration

* A facade should minimize the complexity of subsystem and provide usable interface.
* We can have an interface or abstract class for facade and client can use different subclasses to talk to different subsystems implementations.

#### Real Example

- The java.net.URL class. This class provides a simple method as openStream() and we get an input stream to the resource pointed by the URL object.
	* This class takes care of dealing with multiple classes from the java.net package as well as some internal sun packages.

- Another Real Example: Java Database Connectivity (JDBC)

#### Implementation

```java
package structural.facade;

public interface Hotel {
	public Menus getMenus();
}

//Subsystem Classes
public class NonVegRestaurant implements Hotel {
	public Menus getMenus() {
		NonVegMenu nv = new NonVegMenu();
		return nv;
	}
}

public class VegRestaurant implements Hotel {
	public Menus getMenus() {
		VegMenu v = new VegMenu();
		return v;
	}
}

public class VegNonBothRestaurant implements Hotel {
	public Menus getMenus() {
		Both b = new Both();
		return b;
	}
}
```

```java
package structural.facade;

public interface Menus {

}

import java.util.ArrayList;
import java.util.List;

//Subsystem Classes
public class NonVegMenu implements Menus {
	List<String> items = new ArrayList<>();

	NonVegMenu() {
		items.add("Chicken");
		items.add("FishCurry");
	}

	@Override
	public String toString() {
		return items.toString();
	}
}

public class VegMenu implements Menus {
	List<String> items = new ArrayList<>();

	VegMenu() {
		items.add("Roti");
		items.add("Daal");
	}

	@Override
	public String toString() {
		return items.toString();
	}
}

public class Both implements Menus {
	List<String> items = new ArrayList<>();

	Both() {
		items.add("Roti");
		items.add("FishCurry");
	}

	@Override
	public String toString() {
		return items.toString();
	}
}
```

```java
package structural.facade;

public enum ResturantTypeEnum {
	VEG, NON_VEG, BOTH;
}

//Facade class
public class HotelKeeper {

	private VegMenu getVegMenu() {
		VegRestaurant v = new VegRestaurant();
		VegMenu vegMenu = (VegMenu) v.getMenus();
		return vegMenu;
	}

	private NonVegMenu getNonVegMenu() {
		NonVegRestaurant v = new NonVegRestaurant();
		NonVegMenu NonvegMenu = (NonVegMenu) v.getMenus();
		return NonvegMenu;
	}

	private Both getVegNonMenu() {
		VegNonBothRestaurant v = new VegNonBothRestaurant();
		Both bothMenu = (Both) v.getMenus();
		return bothMenu;
	}

	public Menus getMenu(ResturantTypeEnum resturantTypeEnum) {
		switch (resturantTypeEnum) {
		case VEG:
			return this.getVegMenu();
		case NON_VEG:
			return this.getNonVegMenu();
		case BOTH:
			return this.getVegNonMenu();
		}
		return null;
	}

}

//Client
public class FacadeExample {
	public static void main(String args[]) {
		HotelKeeper hotelKeeper = new HotelKeeper();

		Menus menu = hotelKeeper.getMenu(ResturantTypeEnum.VEG);

		System.out.println("Menu Items: " + menu);

		menu = hotelKeeper.getMenu(ResturantTypeEnum.BOTH);

		System.out.println("Menu Items: " + menu);
	}
}
```

#### Example-2

* consider, a simple banking system.
* The banking system has various operations such as checking account balance, depositing money, withdrawing money, and transferring funds. 
* We will create a BankingFacade to provide a simplified interface for these operations.

**Subsystem Classes**

* represent different parts of the banking system, each handling specific tasks.

```java
class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ". New balance is " + balance);
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds");
        } else {
            balance -= amount;
            System.out.println("Withdrew " + amount + ". New balance is " + balance);
        }
    }
}

class TransferService {
    public void transfer(Account fromAccount, Account toAccount, int amount) {
        if (fromAccount.getBalance() < amount) {
            System.out.println("Insufficient funds for transfer");
        } else {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transferred " + amount + " from Account 1 to Account 2");
        }
    }
}

class NotificationService {
    public void sendNotification(String message) {
        System.out.println("Notification: " + message);
    }
}

class LoanService {
    public void getLoan(String loanType) {
        System.out.println("Processing " + loanType + " loan");
    }
}
```

**Facade Class**

* allowing clients to perform banking tasks without needing to interact directly with the subsystem classes.

```java
public class BankingFacade {
    private Account account;
    private TransferService transferService;
    private NotificationService notificationService;
    private LoanService loanService;

    public BankingFacade(int initialBalance) {
        this.account = new Account(initialBalance);
        this.transferService = new TransferService();
        this.notificationService = new NotificationService();
        this.loanService = new LoanService();
    }

    public void checkBalance() {
        System.out.println("Current balance is: " + account.getBalance());
    }

    public void depositMoney(int amount) {
        account.deposit(amount);
        notificationService.sendNotification("Deposited " + amount + " to your account.");
    }

    public void withdrawMoney(int amount) {
        account.withdraw(amount);
        notificationService.sendNotification("Withdrew " + amount + " from your account.");
    }

    public void transferMoney(Account toAccount, int amount) {
        transferService.transfer(account, toAccount, amount);
        notificationService.sendNotification("Transferred " + amount + " to another account.");
    }

    public void applyForLoan(String loanType) {
        loanService.getLoan(loanType);
        notificationService.sendNotification("Applied for " + loanType + " loan.");
    }
}
```

**Client Code**

```java
public class FacadePatternExample {
    public static void main(String[] args) {
        BankingFacade bankingFacade = new BankingFacade(1000);

        bankingFacade.checkBalance();
        bankingFacade.depositMoney(500);
        bankingFacade.withdrawMoney(200);

        Account anotherAccount = new Account(200);
        bankingFacade.transferMoney(anotherAccount, 300);

        bankingFacade.applyForLoan("Home");
    }
}
```

#### Challenges with facade design pattern

* When the internal structure of a subsystem changes, you need to incorporate the changes in the facade layer also.
* Subsystems are connected with the facade layer. So, you need to take care of an additional layer of coding.

#### Resources

* [https://www.geeksforgeeks.org/facade-design-pattern-introduction/](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [https://howtodoinjava.com/design-patterns/structural/facade-design-pattern/](https://howtodoinjava.com/design-patterns/structural/facade-design-pattern/)