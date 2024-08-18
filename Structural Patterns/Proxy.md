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

#### Real-World Example: 

1. **Java's java.lang.reflect.Proxy**

* allows you to create dynamic proxy classes at runtime.



#### References

* [https://www.geeksforgeeks.org/proxy-design-pattern/](https://www.geeksforgeeks.org/proxy-design-pattern/)
* [https://howtodoinjava.com/design-patterns/structural/proxy-design-pattern/](https://howtodoinjava.com/design-patterns/structural/proxy-design-pattern/)