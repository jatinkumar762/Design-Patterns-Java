#### Introduction

* When we want to enhance behaviour of our existing object dynamically as and when required
* Decorator wraps an object within itself and provides same interface as the wrapped object. So the client of original object does not need to change.
* A decorator provides alternative to subclassing for extending functionality of existing classes.

#### Key Concepts:

1. **Component:** The interface or abstract class defining the methods that will be implemented.

2. **ConcreteComponent:** 

* The class that implements the Component interface.
* This is the object to which additional responsibilities can be added.

3. **Decorator:**

* An abstract class that implements the Component interface and has a Component object.
* It serves as the base class for all decorators.

4. **ConcreteDecorator:**

* The classes that extend the Decorator class and add responsibilities to the Component.


#### Implementation Example

```java
package structural.decorator;

//Base Interface or Component
public interface Message {
	String getContent();
}

/**
 * Concrete component
 * Object to be decorated
 */
public class TextMessage implements Message {

	String mssg;

	TextMessage(String mssg) {
		this.mssg = mssg;
	}

	@Override
	public String getContent() {
		return this.mssg;
	}
}

/**
 * Decorator.
 * Implements component interface
 */
public class UpperCaseMessage implements Message {
	Message mssg;

	UpperCaseMessage(Message mssg) {
		this.mssg = mssg;
	}
	
	@Override
	public String getContent() {
		return mssg.getContent().toUpperCase();
	}
}

/**
 * Decorator
 */
public class Base64EncodedMessage implements Message {
	Message mssg;

	Base64EncodedMessage(Message mssg) {
		this.mssg = mssg;
	}

	@Override
	public String getContent() {
		return Base64.getEncoder().encodeToString(mssg.getContent().getBytes());
	}
}

public class Client {
	public static void main(String[] args) {
		Message txtMessage = new TextMessage("<Hello>, How are you?");
		System.out.println(txtMessage.getContent());

		Message decorator = new UpperCaseMessage(txtMessage);
		System.out.println(decorator.getContent());
		
		decorator = new Base64EncodedMessage(decorator);
		System.out.println(decorator.getContent());
	}
}
```

#### Example-2

* we have a Coffee interface and different types of coffee and add-ons (like milk, sugar, etc.) using the decorator pattern.

**Step 1: Create the Component interface**

```java
public interface Coffee {
    String getDescription();
    double getCost();
}
```

**Step 2: Create the ConcreteComponent class**

```java
public class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 5.0;
    }
}
```

**Step 3: Create the Decorator abstract class**

```java
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }
}
```

**Step 4: Create ConcreteDecorator classes**

```java
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 1.5;
    }
}

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.5;
    }
}
```

**Step 5: Using the decorators**

```java
public class DecoratorPatternTest {
    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());

        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " $" + coffee.getCost());
    }
}
```

**Output**

```
Simple Coffee $5.0
Simple Coffee, Milk $6.5
Simple Coffee, Milk, Sugar $7.0
```


#### Implementation Considerations

* Pay attention to equals and hashcode methods of decorator. When using decorators, you have to decide if decorated object is equal to same instance without decorator.
* Decorator support recursive composition. Difficult to debug sometimes.

#### Design Considerations

* Decorators are more powerful & flexible than inheritance. Inheritance is static by definition but decorator allows you to dynamically compose behaviour using object at runtime. (At runtime we can decide which object is to include or not)
* Do not change meaning of original operation. It should add helpful small behaviour to object's original behaviour.

#### Example of a Decorator

* Classes in Java's I/O package 
* the java.io.BufferedOutputStream class decorates any java.io.OutputStream object and adds buffering to file writing operation. This improves the disk i/o performance by reducing number of writes.

#### Drawbacks

* Often results in large number of classes being added to system, where each class adds a small amount of functionality. You often end up with lot of objects, one nested inside other and so on.

#### Summary

* We use it, when we want to add small behaviour on top of existing object.
* A decorator has the same interface as the object it decorates or contains.
* Decorator allows you to dynamically construct behaviour by using composition.

#### Reference

* [https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects/](https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects/)
