
#### Introduction

* The Prototype Design Pattern is a creational design pattern that allows you to create new objects by copying existing ones, known as prototypes.
* This pattern is useful when creating an object is costly in terms of time or resources, or when you want to avoid the complexity of directly creating instances of complex objects.

#### Key Concepts

1. **Prototype:** An interface or abstract class that declares the method for cloning itself.

2. **Concrete Prototype:** A class that implements the Prototype interface and provides the actual cloning method.

3. **Client:** The client that creates a new object by asking a prototype to clone itself.

#### Example

Let's consider an example where we have different types of shapes, and we want to create new shapes by cloning existing ones.

**Step 1: Define the Prototype Interface**

```java
public interface Shape extends Cloneable {
    Shape clone();
    void draw();
}
```

**Note: The Cloneable Interface**

1. The Cloneable interface in Java is a marker interface, meaning it does not contain any methods. It is used to indicate that a class allows its objects to be cloned. 
2. When a class implements the Cloneable interface, it signals that the clone method of the Object class can be called to create a copy of its instances.

**Step 2: Create Concrete Prototypes**

```java
public class Circle implements Shape {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public Shape clone() {
        try {
            return (Circle) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Circle with radius " + radius);
    }
}

public class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public Shape clone() {
        try {
            return (Rectangle) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle with width " + width + " and height " + height);
    }
}
```

**Step 3: Create a Prototype Registry**

A registry that holds a set of pre-defined prototypes and provides a way to retrieve and clone them.

```java
import java.util.HashMap;
import java.util.Map;

public class PrototypeRegistry {
    private Map<String, Shape> prototypes = new HashMap<>();

    public PrototypeRegistry() {
        // Register default prototypes
        prototypes.put("Circle", new Circle(5));
        prototypes.put("Rectangle", new Rectangle(10, 20));
    }

    public Shape getPrototype(String type) {
        Shape prototype = prototypes.get(type);
        return (prototype != null) ? prototype.clone() : null;
    }
}
```

**Step 4: Client Code**

```java
public class PrototypePatternDemo {
    public static void main(String[] args) {
        PrototypeRegistry registry = new PrototypeRegistry();

        Shape clonedCircle = registry.getPrototype("Circle");
        clonedCircle.draw();

        Shape clonedRectangle = registry.getPrototype("Rectangle");
        clonedRectangle.draw();
    }
}
```

#### Benefits of the Prototype Pattern

- **Avoids the cost of creating complex objects:** If creating an object is expensive in terms of time or resources, cloning an existing object can be more efficient.

- **Simplifies the creation of objects:** Instead of creating objects from scratch, you can create a prototype and clone it whenever needed.

- **Reduces the need for subclasses:** You can create different configurations of an object by cloning and modifying the prototype rather than creating multiple subclasses.

#### Drawbacks

- **Cloning complex objects can be difficult:** If an object has complex internal structures, ensuring that all parts of the object are correctly cloned can be challenging.

- **Requires careful implementation:** Cloning methods need to be implemented carefully to avoid issues with deep and shallow copying, especially with objects that contain references to other objects.


#### Note: The clone Method

The clone method in the Object class is a protected method that creates and returns a copy of the object. For a class to allow cloning, it must:

1. Implement the Cloneable interface.

2. Override the clone method from the Object class and make it public.

**Example**

```java
class PrototypeExample implements Cloneable {
    private String name;
    private int value;

    public PrototypeExample(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public PrototypeExample clone() {
        try {
            return (PrototypeExample) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "PrototypeExample{name='" + name + "', value=" + value + "}";
    }

    public static void main(String[] args) {
        PrototypeExample original = new PrototypeExample("Original", 42);
        PrototypeExample clone = original.clone();

        System.out.println("Original: " + original);
        System.out.println("Clone: " + clone);
    }
}
```