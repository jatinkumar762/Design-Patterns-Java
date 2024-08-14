#### Introduction
* need to avoid tight coupling the code which sends request to the code which handles that request.
* Typically the code which wants some requested calls the exact method on an exact object to process it, thus the tight coupling. 
* Chain of responsibility solves this problem by giving more than one object, chance to process the request.
* We create objects which are chained together by one object knowing reference of object which is next in chain. 
* We give request to first object in chain, if it can't handle that it simply passes the request down the chain.

#### Implementation Steps
* We start by defining handler interface/abstract class 
  * Handler must define a method to accept incoming request 
  * Handler can define method to access successor in chain. If it's an abstract class then we can even maintain successor.
* Next we implement handler in one or more concrete handlers.Concrete handler should check if it can handle the request. If not then it should pass request to next handler.
* We have to create our chain of objects next. We can do it in client. Typically in real world this job will be done by some framework or initialization code written by you.
* Client needs to know only the first object in chain. It'll pass on request to this object

#### Implementation

**Step-1** Define the Handler Interface

```java
public abstract class SupportHandler {
    protected SupportHandler successor;

    public void setSuccessor(SupportHandler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(int request);
}
```

**Step-2** Create Concrete Handler Classes

```java
public class Level1Support extends SupportHandler {
    @Override
    public void handleRequest(int request) {
        if (request < 10) {
            System.out.println("Level 1 handling request " + request);
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}

public class Level2Support extends SupportHandler {
    @Override
    public void handleRequest(int request) {
        if (request < 20) {
            System.out.println("Level 2 handling request " + request);
        } else if (successor != null) {
            successor.handleRequest(request);
        }
    }
}

public class Level3Support extends SupportHandler {
    @Override
    public void handleRequest(int request) {
        System.out.println("Level 3 handling request " + request);
    }
}
```

**Step 3:** Create the Chain and Handle Requests

```java
public class Main {
    public static void main(String[] args) {
        // Create the chain of responsibility
        SupportHandler level3 = new Level3Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level1 = new Level1Support();

        level1.setSuccessor(level2);
        level2.setSuccessor(level3);

        // Handle requests
        int[] requests = {5, 15, 25};

        for (int request : requests) {
            level1.handleRequest(request);
        }
    }
}
```

**Output**

```
Level 1 handling request 5
Level 2 handling request 15
Level 3 handling request 25
```

#### Practical Use Cases

1. Customer Support System
In a customer support system, a request or complaint may be handled by different levels of support personnel based on its complexity or severity. For instance:

* A basic inquiry is handled by Level 1 support.
* A more complex issue is escalated to Level 2 support.
* Critical problems are handled by Level 3 support.

2. Logging Framework
In a logging framework, different log levels (e.g., debug, info, warning, error) might be handled by different loggers. For instance:

* A debug logger handles debug messages.
* An info logger handles informational messages.
* An error logger handles error messages.

#### Drawbacks
* There is no guarantee provided in the pattern that a request will be handled.
* It is easy to misconfigure the chain when we are connecting successors. There is nothing in the pattern that will let us know of any such problems. Some handlers may be left unconnected to chain.


#### References
* https://www.geeksforgeeks.org/chain-responsibility-design-pattern/
* https://howtodoinjava.com/design-patterns/behavioral/chain-of-responsibility-design-pattern/


