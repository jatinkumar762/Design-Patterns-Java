
### Introduction
* Adapter design pattern is one of the structural design pattern and its used so that two unrelated interfaces can work together. 
* The object, that joins these unrelated interfaces, is called an Adapter.

#### Use Case
* We have an existing object which provides the functionality that client needs. But client code can't use this object because it expects an object with different interface.
* Using adapter design pattern we make this existing object work with client by adapting the object to client's expected interface.

### Example
* The main use of this pattern is when a class that you need to use doesn’t meet the requirements of an interface.
* BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  1. System.in is static instance of InputStream, reads the data from the console in bytes stream.
  2. BufferedReader, reads a character stream.
  3. System.in provides byte stream where BufferedReader expects character stream. How they will work together?
  4. ideal situation to put a adapter in between two incompatible interfaces.
  5. InputStreamReader does exactly this thing and works adapter between System.in and BufferedReader.

### Implementation

```java
/**
 * 
 * Interface expected by client
 *
 */
interface Target {
	void operation();
}

/**
 * 
 * our existing class providing
 * needed functionality
 */
interface Adaptee {
	void myOperation();
}

/**
 * 
 * Adapts existing functionality to 
 * target interface
 * 
 * Using composition instead 
 * of Inheritance
 */
class ObjectAdapter implements Target {

	Adaptee adaptee;

	ObjectAdapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	public void operation() {
		adaptee.myOperation();
	}
} 
```






### Useful Links
* [howtodoinjava - Adapter Design Pattern in Java](https://howtodoinjava.com/design-patterns/structural/adapter-design-pattern-in-java/)