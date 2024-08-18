#### Introduction

* Similar to Decorator pattern
* this design pattern provides that an object representing another object.
* Useful to create a wrapper to cover the main object’s complexity from the client
* It acts as an intermediary between the client and the target object to control access, add additional functionalities, or delay the object creation until it's necessary.

![Design](../images/ProxyArchitecture.png)

#### Key Concepts

1. **Proxy:**

* An intermediary object that controls access to the real object. It implements the same interface as the real object.

2. **Real Subject:**

* The actual object that the proxy represents and controls access to.

3. **Subject:**

* An interface that both the Real Subject and Proxy implement.


#### Example

```java
public interface Internet 
{ 
	public void connectTo(String serverhost) throws Exception; 
} 

public class RealInternet implements Internet 
{ 
	@Override
	public void connectTo(String serverhost) 
	{ 
		System.out.println("Connecting to "+ serverhost); 
	} 
}

public class ProxyInternet implements Internet 
{
  private Internet internet = new RealInternet(); 
  //or
  //we can implement RealInternet class also
  //CLient will interact with ProxyInternet object

}
```

#### Example-2

**Step 1: Create the Subject interface**

```java
public interface Image {
    void display();
}
```

**Step 2: Create the Real Subject class**

```java
public class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadImageFromDisk();
    }

    private void loadImageFromDisk() {
        System.out.println("Loading " + filename);
    }

    @Override
    public void display() {
        System.out.println("Displaying " + filename);
    }
}
```

**Step 3: Create the Proxy class**

```java
public class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
```

**Step 4: Use the Proxy in the client code**

```java
public class ProxyPatternExample {
    public static void main(String[] args) {
        Image image = new ProxyImage("test_image.jpg");

        // Image will be loaded from disk
        image.display();
        System.out.println("");

        // Image will not be loaded from disk
        image.display();
    }
}
```

#### Real-World Example: 

1. **Java's java.lang.reflect.Proxy**

* allows you to create dynamic proxy classes at runtime.



#### References

* [https://www.geeksforgeeks.org/proxy-design-pattern/](https://www.geeksforgeeks.org/proxy-design-pattern/)
* [https://howtodoinjava.com/design-patterns/structural/proxy-design-pattern/](https://howtodoinjava.com/design-patterns/structural/proxy-design-pattern/)