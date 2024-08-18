
- This pattern is useful when we have a superclass with multiple sub-classes and based on input, we need to return one of the sub-class.
- Super class can be an interface, abstract class or a normal java class.

### Pros and Cons

|	Pros	|	Cons	|
|	---		|	---		|
|	Avoid tight coupling	|	code becomes complicated when we introduce a lot of new subclasses to implement the pattern|
|	Single Responsibility Principle. Product creation code at one place in the program. |
|	Open/Closed Principle. We can introduce new types of products into the program|


#### Example-1

```java

//Product: The interface or abstract class defining the object to be created.
abstract class Computer {
	public abstract String getRAM();
	public abstract String getHDD();
	public abstract String getCPU();
	
	@Override
	public String toString() {
		return "RAM= "+this.getRAM()+", HDD="+this.getHDD()+", CPU="+this.getCPU();
	}
}

//Concrete Product: Specific implementations of the product.
class PC extends Computer{
	
	private String ram;
	private String hdd;
	private String cpu;
	
	public PC(String ram, String hdd, String cpu) {
		super();
		this.ram = ram;
		this.hdd = hdd;
		this.cpu = cpu;
	}
	
	@Override
	public String getRAM() {
		return ram;
	}
	@Override
	public String getHDD() {
		return hdd;
	}
	@Override
	public String getCPU() {
		return cpu;
	}
	
}

//Concrete Product: Specific implementations of the product.
class Server extends Computer{
	
	private String ram;
	private String hdd;
	private String cpu;
	
	public Server(String ram, String hdd, String cpu) {
		super();
		this.ram = ram;
		this.hdd = hdd;
		this.cpu = cpu;
	}

	@Override
	public String getRAM() {
		return ram;
	}

	@Override
	public String getHDD() {
		return hdd;
	}

	@Override
	public String getCPU() {
		return cpu;
	}
}


//Concrete Creator: Subclasses that implement the factory method to create concrete products.
class ConcreteComputerFactory{
	
	//the factory method
	public static Computer getInstance(String type, String ram, String hdd, String cpu) {
		
		if("PC".equalsIgnoreCase(type)) {
			return new PC(ram, hdd, cpu);
		}
		else if("Server".equalsIgnoreCase(type)) {
			return new Server(ram, hdd, cpu);
		}
		return null;
	}
}

public class FactoryMethod {

	public static void main(String args[]) {
		
		Computer pc = ComputerFactory.getInstance("pc","2 GB","500 GB","2.4 GHz");
		
		Computer server = ComputerFactory.getInstance("server","16 GB","1 TB","2.9 GHz");
		
		System.out.println("Factory PC Config::"+pc);
		System.out.println("Factory Server Config::"+server);
		
	}	
	
}
```

#### Example-2

* Let's consider an example of a Shape factory that can create different types of shapes like Circle, Rectangle, and Square.

**Step 1: Define the Product Interface**

```java
// Product
public interface Shape {
    void draw();
}
```

**Step 2: Create Concrete Products**

```java
// Concrete Product 1
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

// Concrete Product 2
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

// Concrete Product 3
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Square");
    }
}
```

**Step 3: Create the Factory**

```java
// Creator
public abstract class ShapeFactory {
    abstract Shape createShape(String shapeType);
}

// Concrete Creator
public class ConcreteShapeFactory extends ShapeFactory {
    @Override
    public Shape createShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}
```

**Step 4: Use the Factory to Create Objects**

```java
public class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ConcreteShapeFactory();

        // Get an object of Circle and call its draw method.
        Shape shape1 = shapeFactory.createShape("CIRCLE");
        shape1.draw();

        // Get an object of Rectangle and call its draw method.
        Shape shape2 = shapeFactory.createShape("RECTANGLE");
        shape2.draw();

        // Get an object of Square and call its draw method.
        Shape shape3 = shapeFactory.createShape("SQUARE");
        shape3.draw();
    }
}
```

### References
	
- [https://www.journaldev.com/1392/factory-design-pattern-in-java](https://www.journaldev.com/1392/factory-design-pattern-in-java)
