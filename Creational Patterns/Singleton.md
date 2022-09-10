
* Introduction
* Eager v/s Lazy Loading
* Thread Safety

### Introduction
* When only one instance of a class is needed (shared resource)
* Access to that instance from the whole application (don't allow modify)
* Examples
  * DB connection
  * Logger Instance
  * Runtime in Java
* How to acheive Singleton
  * Make the variable initialized by private constructor
  * Access to be given only by getter method
  * Note - Singleton class never accepts a parameter

### Eager v/s Lazy Loading

* Eager Loading
  * The instance is already initialized as soon as the application is up.
  * Works well with one Singleton class

* Lazy Loading
  * The instance is initialized only when any app Module calls for it. 
  * Suitable for multiple Singleton class

### Implementation

```java
package creational.singleton;

class Logger {
	private static Logger loggerInstance = null;

	private Logger() {
		// stop object creation using Reflection
		if (loggerInstance != null) {
			throw new RuntimeException("Not allowed");
		}
	}

	public static Logger getLoggerInstance() {
		if (loggerInstance == null) {
			synchronized (Logger.class) {
				if (loggerInstance == null) {
					loggerInstance = new Logger();
				}
			}
		}
		return loggerInstance;
	}

}

public class SingletonDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger logger1 = Logger.getLoggerInstance();
		System.out.println(logger1);

		Logger logger2 = Logger.getLoggerInstance();
		System.out.println(logger2);

	}

}
```

### Summary

#### Pros
* Easy to handle access to shared global resource
* Easy to implement and gurantees 1 instance

#### Cons
* Used with parameters and confused with factory
* Thread safety has to be ensured

### Resources
* [sudoCode - Story of Singleton Design Pattern](https://www.youtube.com/watch?v=EZDeEHXUf8w)



