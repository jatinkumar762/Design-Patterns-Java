### Introduction

* Client has to interact with a large number of interfaces and classes in a subsystem to get result. So client get tightly coupled with those interfaces and classes. Facade solves this problem.

* Client interacts with just the facade now to get the same result. 

### Implementation Consideration

* A facade should minimize the complexity of subsystem and provide usabe interface.
* We can have an interface or abstract class for facade and client can use different subclasses to talk to different subsystems implementations.

### Example

* The java.net.URL class. This class provides a simple method as openStream() and we get an input stream to the resource pointed by the URL object.
* This class takes care of dealing with multiple classes from the java.net package as well as some internal sun packages.

### Challenges with facade design pattern

* When the internal structure of a subsystem changes, you need to incorporate the changes in the facade layer also.
* Subsystems are connected with the facade layer. So, you need to take care of an additional layer of coding.

### Resources

* [https://www.geeksforgeeks.org/facade-design-pattern-introduction/](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [https://howtodoinjava.com/design-patterns/structural/facade-design-pattern/](https://howtodoinjava.com/design-patterns/structural/facade-design-pattern/)