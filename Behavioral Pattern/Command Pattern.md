* Useful when we want to avoid multiple if else condition in a code
* An object is used to represent request/command and encapsulate all the information needed to call a method at a later time. 
* This information includes the method name, the object that owns the method and values for the method parameters.
* We can store lists of code that is executed at a later time or many times
* Command object helps in loose coupling between two classes where one class (invoker) shall call a method on other class (receiver) to perform a business operation.


#### Participants for command design pattern are:
  * Command interface - for declaring an operation.
  * Concrete command classes – Implements the Command interface
  * Invoker -   which is given the command object to carry out the operation.
  * Receiver – which execute the operation.
  * Client - Creates a ConcreteCommand object and sets its receiver


#### Positive
  1. We can store multiple commands in a class to use over and over

#### Negative
  1. We create many small classes that store list of commands


#### Implementation Example

```java
//Interface implemented by all concrete
//command classes
public interface Command {
	
	void execute();
}

/*
Light class and its corresponding command classes
This class is the receiver.
*/
class Light
{
    public void on()
    {
        System.out.println("Light is on");
    }
    public void off()
    {
        System.out.println("Light is off");
    }
}

/*
A Concrete implementation of Command.
for each operation of Light (Receiver) we create a command
*/
class LightOnCommand implements Command
{
    Light light;
 
    // The constructor is passed the light it
    // is going to control.
    public LightOnCommand(Light light)
    {
       this.light = light;
    }
    public void execute()
    {
       light.on();
    }
}

class LightOffCommand implements Command
{
    Light light;
    public LightOffCommand(Light light)
    {
        this.light = light;
    }
    public void execute()
    {
         light.off();
    }
}

/*
This is invoker actually executing commands.
A Simple remote control with one button
*/
class SimpleRemoteControl
{
    Command slot;  // only one button
    public SimpleRemoteControl(){
    }
 
    public void setCommand(Command command){
        // set the command the remote will
        // execute
        slot = command;
    }
 
    public void buttonWasPressed(){
        slot.execute();
    }
}

//Driver class
//Client
class RemoteControlTest
{
    public static void main(String[] args)
    {
        //Receiver
        Light livingRoomLight = new Light();

        //associates the Receiver with the ConcreteCommand.
        Command turnOnLight = new TurnOnLightCommand(livingRoomLight);
        Command turnOffLight = new TurnOffLightCommand(livingRoomLight);

        //Invoker
        //Holds a command and calls the execute method when requested.
        SimpleRemoteControl remote = new SimpleRemoteControl();

        // we can change command dynamically
        remote.setCommand(turnOnLight);
        remote.buttonWasPressed();

        remote.setCommand(turnOffLight);
        remote.buttonWasPressed();
     }
}
```

#### Example of Command Pattern

```java
public class CommandPatternWithRunnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new PrintTask());
        thread.start();
    }
}

class PrintTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Print Task Executed");
    }
}
```

* The java.lang.Runnable interface (similar to command interface) represents the Command pattern. 
* We create the object of class implementing runnable, providing all information it needs. 
* In the run method (similar to execute) we'll call an operation on the receiver. 
* We can send this object for later execution to other parts of our application.

#### Summary

* Command pattern allows you to treat requests for operations as objects. This allows you to send these objects to different parts of code for later execution or to a different thread. 
* Commands typically invoke the actual operation on a receiver but contain parameters or information needed for invocation. 
* Client code is responsible for creating instances of command & providing it with receiver and request information. 
* Commands can also implement an undo feature. Here command itself stores a snapshot of receiver. 

- - - -

#### References
* [Command Design Pattern - Derek Banas](https://www.youtube.com/watch?v=7Pj5kAhVBlg)
* [Udemy - Java Design Patterns & SOLID Design Principles](https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects/)
* [https://www.geeksforgeeks.org/command-pattern/](https://www.geeksforgeeks.org/command-pattern/)
