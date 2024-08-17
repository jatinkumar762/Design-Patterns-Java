
* this design pattern, allows an object to change its behavior when its internal state changes.

#### Key Components of the State Design Pattern

1. **State:** 

* Defines an interface for encapsulating the behavior associated with a particular state of the Context.

2. **Concrete States:**

* Implement the State interface and define behavior specific to a particular state of the Context.

3. **Context:**

* Maintains an instance of a ConcreteState subclass that defines the current state.

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
