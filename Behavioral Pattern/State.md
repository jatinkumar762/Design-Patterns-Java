
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

### Example

```java

interface PlayerState{
    void pressPlay(MediaPlayer player);
    void pressPause(MediaPlayer player);
    void pressMute(MediaPlayer player);
    void pressPower(MediaPlayer player);
    void printStatus();
}

enum State{
    OFF, PLAYING, PAUSED, MUTED;
}

class Logger {

    public static void logMessage(String message){
        System.out.println(message);
    }
}

// Off State
class OffState implements PlayerState {

    @Override
    public void pressPlay(MediaPlayer player) {
        Logger.logMessage("Turning on and starting playback");
        player.setState(State.PLAYING);
    }

    @Override
    public void pressPause(MediaPlayer player) {
        Logger.logMessage("Cannot pause - player is off");
    }

    @Override
    public void pressMute(MediaPlayer player) {
        Logger.logMessage("Cannot mute - player is off");
    }

    @Override
    public void pressPower(MediaPlayer player) {
        Logger.logMessage("Player is already off");
    }

    @Override
    public void printStatus() {
        Logger.logMessage("Player is OFF");
    }
}

// Playing State
class PlayingState implements PlayerState {
    @Override
    public void pressPlay(MediaPlayer player) {
        Logger.logMessage("Already playing");
    }

    @Override
    public void pressPause(MediaPlayer player) {
        Logger.logMessage("Pausing playback");
        player.setState(State.PAUSED);
    }

    @Override
    public void pressMute(MediaPlayer player) {
        Logger.logMessage("Muting player");
        player.setState(State.MUTED);
    }

    @Override
    public void pressPower(MediaPlayer player) {
        Logger.logMessage("Turning off player");
        player.setState(State.OFF);
    }

    @Override
    public void printStatus() {
        Logger.logMessage("Player is PLAYING");
    }
}

// Paused State
class PausedState implements PlayerState {
    @Override
    public void pressPlay(MediaPlayer player) {
       Logger.logMessage("Resuming playback");
       player.setState(State.PLAYING);
    }

    @Override
    public void pressPause(MediaPlayer player) {
        Logger.logMessage("Already paused");
    }

    @Override
    public void pressMute(MediaPlayer player) {
        Logger.logMessage("Cannot mute while paused");
    }

    @Override
    public void pressPower(MediaPlayer player) {
        Logger.logMessage("Turning off player");
        player.setState(State.OFF);
    }

    @Override
    public void printStatus() {
        Logger.logMessage("Player is PAUSED");
    }
}

// Muted State
class MutedState implements PlayerState {
    @Override
    public void pressPlay(MediaPlayer player) {
        Logger.logMessage("Already playing (muted)");
    }

    @Override
    public void pressPause(MediaPlayer player) {
        Logger.logMessage("Pausing muted playback");
        player.setState(State.PAUSED);
    }

    @Override
    public void pressMute(MediaPlayer player) {
        Logger.logMessage("Unmuting player");
        player.setState(State.MUTED);
    }

    @Override
    public void pressPower(MediaPlayer player) {
        Logger.logMessage("Turning off player");
        player.setState(State.OFF);
    }

    @Override
    public void printStatus() {
        Logger.logMessage("Player is PLAYING (MUTED)");
    }
}

class MediaPlayer {
    private State state;
    private OffState offState;
    private PlayingState playingState;
    private PausedState pausedState;
    private MutedState mutedState;

    MediaPlayer() {
        this.state = State.OFF;
        this.offState = new OffState();
        this.playingState = new PlayingState();
        this.pausedState = new PausedState();
        this.mutedState = new MutedState();
    }

    public void setState(State state) {
        this.state = state;
    }

    // Button press handlers
    public void pressPlay() {

        PlayerState currentState = getPlayerState();

        currentState.pressPlay(this);
    }

    public void pressPause() {

        PlayerState currentState = getPlayerState();

        currentState.pressPause(this);
    }

    public void pressMute() {
        PlayerState currentState = getPlayerState();

        currentState.pressMute(this);
    }

    public void pressPower() {
        PlayerState currentState = getPlayerState();

        currentState.pressPower(this);
    }

    public void printStatus() {
        PlayerState currentState = getPlayerState();
        currentState.printStatus();
    }


    private PlayerState getPlayerState(){

        switch (state) {
            case OFF -> {
                return offState;
            }
            case PLAYING -> {
                return playingState;
            }
            case MUTED -> {
                return mutedState;
            }
            case PAUSED -> {
                return pausedState;
            }
            default -> throw new IllegalArgumentException("Unknown state: " + state);
        }
    }
}

public class StateDesignPatternDemo {

    public static void main(String args[]){

        MediaPlayer player = new MediaPlayer();

        Logger.logMessage("--- Initial state ---");
        player.printStatus();

        Logger.logMessage("\n--- Turning on ---");
        player.pressPlay();  // Turns on and starts playing
        player.printStatus();

        Logger.logMessage("\n--- Pausing ---");
        player.pressPause();
        player.printStatus();

        Logger.logMessage("\n--- Resuming ---");
        player.pressPlay();
        player.printStatus();

        Logger.logMessage("\n--- Muting ---");
        player.pressMute();
        player.printStatus();

        Logger.logMessage("\n--- Trying to pause while muted ---");
        player.pressPause();
        player.printStatus();

        Logger.logMessage("\n--- Unmuting ---");
        player.pressMute();
        player.printStatus();

        Logger.logMessage("\n--- Turning off ---");
        player.pressPower();
        player.printStatus();

        Logger.logMessage("\n--- Trying to pause while off ---");
        player.pressPause();
        player.printStatus();
    }
}
```


### Example

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
