
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


### Eager Initialization (Eager Loading)

```java
class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}
```

**Pros:** 
- Simple to implement.
- Thread-safe without synchronization.

**Cons:**
- Instance is created even if it's never used — might waste memory or CPU.

### Lazy Initialization (Lazy Loading)

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

	//public static synchronized Logger getLoggerInstance()
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

#### Alternative Singleton Implementation (Lazy Initialization Holder Class)

*  thread safety and lazy initialization without explicit synchronization

```java
public class Logger {
    // Private constructor to prevent instantiation
    private Logger() {
        // Initialization code
    }

    // Static inner class responsible for holding the singleton instance
    private static class LoggerHolder {
        private static final Logger INSTANCE = new Logger();
    }

    // Public static method to provide access to the single instance
    public static Logger getInstance() {
        return LoggerHolder.INSTANCE;
    }

    // Method to log messages
    public void log(String message) {
        System.out.println("Log: " + message);
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

### Drawbacks of the Singleton Design Pattern

1. Global State Problems

- Issue: Singletons introduce global variables into your application
- Consequences:
  
  * Can be modified from anywhere in the codebase

2. Tight Coupling

- Issue: Classes using the Singleton become tightly coupled to the concrete Singleton class
- Consequences:

  * Violates Dependency Inversion Principle
  * Reduces code flexibility and maintainability

3. Testing Difficulties

- Issue: Singleton state persists between tests
- Consequences:
  
  * Tests can't be isolated from each other
  * Requires careful resetting of state between tests
  * Makes parallel test execution problematic

- Example

```java
// Test A modifies Singleton state
Database.getInstance().setConfig(...);

// Test B unexpectedly gets modified state
Database.getInstance().query(...); 
```

4. Concurrency Challenges

- Issue: Thread safety must be carefully implemented

5. Inheritance Limitations

- Issue: Singletons can't be subclassed (private constructor)
- Consequences:
  
  * Limits polymorphism and extensibility
  * Violates Open/Closed Principle
  * Makes it hard to create variations of the Singleton

6. Lifecycle Management Problems

- Issue: Singleton lifetime is typically the entire application lifetime
- Consequences:

  * Can't dispose/recreate when needed
  * Memory leaks if Singleton holds heavy resources


### When Singleton Might Be Acceptable

- When the instance is truly stateless (utility classes)
- For logging frameworks (where global access is actually desired)
- Hardware access points (where multiple instances don't make sense)
- When using immutable objects

### Resources
* [sudoCode - Story of Singleton Design Pattern](https://www.youtube.com/watch?v=EZDeEHXUf8w)



