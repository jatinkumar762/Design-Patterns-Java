

#### Introduction

* facade (a false or misleading appearance)

* Client has to interact with a large number of interfaces and classes in a subsystem to get result. So client get tightly coupled with those interfaces and classes. Facade solves this problem.

* Client interacts with just the facade now to get the same result. 

#### Implementation Consideration

* A facade should minimize the complexity of subsystem and provide usabe interface.
* We can have an interface or abstract class for facade and client can use different subclasses to talk to different subsystems implementations.

#### Example

* The java.net.URL class. This class provides a simple method as openStream() and we get an input stream to the resource pointed by the URL object.
* This class takes care of dealing with multiple classes from the java.net package as well as some internal sun packages.

#### Implementation

```java
package structural.facade;

public interface Hotel {
	public Menus getMenus();
}

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

#### Challenges with facade design pattern

* When the internal structure of a subsystem changes, you need to incorporate the changes in the facade layer also.
* Subsystems are connected with the facade layer. So, you need to take care of an additional layer of coding.

#### Resources

* [https://www.geeksforgeeks.org/facade-design-pattern-introduction/](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [https://howtodoinjava.com/design-patterns/structural/facade-design-pattern/](https://howtodoinjava.com/design-patterns/structural/facade-design-pattern/)