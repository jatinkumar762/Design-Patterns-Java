
## Introduction

* The Flyweight pattern is like a library of reusable objects - instead of creating new objects every time, you share existing ones to save memory.

**When to Use Flyweight:**

- When your application uses a large number of similar objects
- When storage costs are high due to object quantity
- When most object state can be made extrinsic (unique per usage)
- Common use cases: Text processing, game development (trees/grass/bullets), GUI systems
- Real-World Flyweight Pattern Example: Online Game with Trees

**Benefits:**

- Reduces memory usage by sharing objects
- Improves performance by reducing object creation overhead

**Trade-offs:**

- Increases code complexity
- May introduce thread-safety concerns in concurrent environments

## Java Libraries That Use the Flyweight Pattern

1. Java String Pool

```java
String s1 = "Hello";  // Uses flyweight from string pool
String s2 = "Hello";  // Reuses same instance
String s3 = new String("Hello");  // Creates new object (not from pool)

System.out.println(s1 == s2); // true - same object
System.out.println(s1 == s3); // false - different objects
```

2. java.lang.Integer.valueOf()

- The Integer cache for small numbers (-128 to 127):

```java
Integer i1 = Integer.valueOf(127);  // Uses cached instance
Integer i2 = Integer.valueOf(127);  // Reuses same instance
Integer i3 = new Integer(127);      // Creates new object

System.out.println(i1 == i2); // true
System.out.println(i1 == i3); // false
```

3. Java Collections - Collections.emptyList()

- Returns a shared immutable empty list instance:

```java
List<String> empty1 = Collections.emptyList();
List<String> empty2 = Collections.emptyList();

System.out.println(empty1 == empty2); // true - same instance
```

4. Java AWT - Color Class

- Some color constants are flyweights:

```java
Color red1 = Color.RED;
Color red2 = Color.RED;

System.out.println(red1 == red2); // true
```

5. Apache Commons Pool

- Provides object pooling implementations (a related concept)

6. Java Concurrency - Executors

- Thread pools reuse worker threads: