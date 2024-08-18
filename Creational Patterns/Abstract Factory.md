
* provides an interface for creating families of related or dependent objects without specifying their concrete classes
* It involves multiple factory methods for different types of products, grouped together in one factory interface, and concrete factories that implement this interface.

#### Key Concepts

1. **Abstract Factory:** Declares an interface for creating abstract product objects.

2. **Concrete Factory:** Implements the operations to create concrete product objects.

3. **Abstract Product:** Declares an interface for a type of product object.

4. **Concrete Product:** Implements the abstract product interface.

5. **Client:** Uses only the interfaces declared by abstract factory and abstract product classes.

#### Example-1

Consider an example where we have different types of UI components (Buttons and Checkboxes) for different operating systems (Windows and Mac).

**Step 1: Define Abstract Products**

```java
// Abstract Product 1
public interface Button {
    void paint();
}

// Abstract Product 2
public interface Checkbox {
    void paint();
}
```

**Step 2: Create Concrete Products**

```java
// Concrete Product for Windows Button
public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in Windows style.");
    }
}

// Concrete Product for Mac Button
public class MacButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in Mac style.");
    }
}

// Concrete Product for Windows Checkbox
public class WindowsCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in Windows style.");
    }
}

// Concrete Product for Mac Checkbox
public class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in Mac style.");
    }
}
```

**Step 3: Define Abstract Factory**

```java
// Abstract Factory
public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}
```

**Step 4: Create Concrete Factories**

```java
// Concrete Factory for Windows
public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

// Concrete Factory for Mac
public class MacFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
```

**Step 5: Create Client Code**

```java
public class Application {
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}

public class ApplicationConfigurator {
    public static void main(String[] args) {
        Application app;
        GUIFactory factory;

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            factory = new MacFactory();
        } else {
            factory = new WindowsFactory();
        }

        app = new Application(factory);
        app.paint();
    }
}
```


#### Example-2

```java
package creational;

abstract class Chair{
	abstract public int getPrice(); 
	abstract public void setPrice(int price); 
	@Override
	public String toString() {
		return "ChairA with price: "+this.getPrice();
	}
}

class ChairA extends Chair{
	
	private int price;
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}	
}

class ChairB extends Chair{
	
	private int price;
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}


abstract class AbstractFactory{
	abstract Chair getChairInstance(); 
}

class FactoryA extends AbstractFactory{

	@Override
	Chair getChairInstance() {
		// TODO Auto-generated method stub
		return new ChairA();
	}
	
}

class FactoryB extends AbstractFactory{

	@Override
	Chair getChairInstance() {
		// TODO Auto-generated method stub
		return new ChairB();
	}
	
}


public class AbstractFactoryPattern {
	
	public static void main(String args[]) {
		
		AbstractFactory factoryA = new FactoryA();
		
		Chair chair = factoryA.getChairInstance();
		chair.setPrice(1000);
		
		System.out.println(chair);
		
		
	}

}
```