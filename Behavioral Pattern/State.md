
* this design pattern, allows an object to change its behavior when its internal state changes.

#### Key Components of the State Design Pattern

1. **State:** 

* Defines an interface for encapsulating the behavior associated with a particular state of the Context.

2. **Concrete States:**

* Implement the State interface and define behavior specific to a particular state of the Context.

3. **Context:**

* Maintains an instance of a ConcreteState subclass that defines the current state.

    **The Context class is a wrapper class that:**

    1. Maintains a reference to the current strategy/state
    2. Provides an interface for clients to interact with
    3. Delegates work to the current strategy/state object
    4. May handle transitions between different strategies/states

#### Example

**Step 1:** Define the State Interface

```java
interface TrafficLightState {
    void changeLight(TrafficLightContext context);
}
```

**Step 2:** Create Concrete State Classes

```java
class RedLightState implements TrafficLightState {
    @Override
    public void changeLight(TrafficLightContext context) {
        System.out.println("Red Light - Stop");
        context.setState(new GreenLightState());
    }
}

class YellowLightState implements TrafficLightState {
    @Override
    public void changeLight(TrafficLightContext context) {
        System.out.println("Yellow Light - Prepare to Stop");
        context.setState(new RedLightState());
    }
}

class GreenLightState implements TrafficLightState {
    @Override
    public void changeLight(TrafficLightContext context) {
        System.out.println("Green Light - Go");
        context.setState(new YellowLightState());
    }
}
```

**Step 3:** Create the Context Class

```java
class TrafficLightContext {
    private TrafficLightState currentState;

    public TrafficLightContext() {
        // Initial state
        this.currentState = new RedLightState();
    }

    public void setState(TrafficLightState state) {
        this.currentState = state;
    }

    public void changeLight() {
        currentState.changeLight(this);
    }
}
```

**Step 4:** Use the State Pattern

```java
public class StatePatternExample {
    public static void main(String[] args) {
        TrafficLightContext trafficLight = new TrafficLightContext();

        for (int i = 0; i < 6; i++) {
            trafficLight.changeLight();
        }
    }
}
```

#### Example-2 Vending machine

```java
interface VendingMachineState {
    void handleRequest();
}

class ReadyState implements VendingMachineState {
    @Override
    public void handleRequest() {
        System.out.println("Ready state: Please select a product.");
    }
}

class ProductSelectedState implements VendingMachineState {
    @Override
    public void handleRequest() {
        System.out.println("Product selected state: Processing payment.");
    }
}

class PaymentPendingState implements VendingMachineState {
    @Override
    public void handleRequest() {
        System.out.println("Payment pending state: Dispensing product.");
    }
}

class OutOfStockState implements VendingMachineState {
    @Override
    public void handleRequest() {
        System.out.println("Out of stock state: Product unavailable. Please select another product.");
    }
}

class VendingMachineContext {
    private VendingMachineState state;

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public void request() {
        state.handleRequest();
    }
}

public class Main {
    public static void main(String[] args) {
        // Create context
        VendingMachineContext vendingMachine = new VendingMachineContext();

        // Set initial state
        vendingMachine.setState(new ReadyState());

        // Request state change
        vendingMachine.request();

        // Change state
        vendingMachine.setState(new ProductSelectedState());

        // Request state change
        vendingMachine.request();

        // Change state
        vendingMachine.setState(new PaymentPendingState());

        // Request state change
        vendingMachine.request();

        // Change state
        vendingMachine.setState(new OutOfStockState());

        // Request state change
        vendingMachine.request();
    }
}
```


#### Real-world Examples in Java

1. **Java's Thread Class**

Java's Thread class uses the state pattern. A thread can be in one of several states, such as NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, or TERMINATED.

```java
public class ThreadStateExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(thread.getState()); // NEW
        thread.start();
        System.out.println(thread.getState()); // RUNNABLE

        Thread.sleep(500);
        System.out.println(thread.getState()); // TIMED_WAITING

        thread.join();
        System.out.println(thread.getState()); // TERMINATED
    }
}
```

#### When to use the State Design Pattern

* **Multiple states with distinct behaviors:**

* **Frequent state changes:**

* **Adding new states easily:**

#### When not to use the State Design Pattern

* **Few states with simple behavior:** If your object has only a few simple states with minimal behavioral differences, the overhead of the State pattern outweighs its benefits. In such cases, simpler conditional logic within the object itself might suffice.

* **Performance-critical scenarios:** The pattern can introduce additional object creation and method calls, potentially impacting performance. If performance is paramount, a different approach might be more suitable.

* **Over-engineering simple problems:** Don’t apply the pattern just for the sake of using a design pattern. If your logic is clear and maintainable without it, stick with the simpler solution.

#### Reference
* https://www.geeksforgeeks.org/state-design-pattern/
