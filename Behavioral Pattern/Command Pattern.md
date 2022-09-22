* Useful when we want to avoid multiple if else condition in a code
* An object is used to represent and encapsulate all the information needed to call a method at a later time. 
* This information includes the method name, the object that owns the method and values for the method parameters. This object is called command.
* We can store lists of code that is executed at a later time or many times
* Command object helps in loose coupling between two classes where one class (invoker) shall call a method on other class (receiver) to perform a business operation.

- - - -

###### Participants for command design pattern are:
  * Command interface - for declaring an operation.
  * Concrete command classes –
  * Invoker -   which is given the command object to carry out the operation.
  * Receiver – which execute the operation.

- - - -

###### Positive
  1. We can store multiple commands in a class to use over and over

###### Negative
  1. We create many small classes that store list of commands

- - - -

###### Implementation Example

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

// Driver class
//Client
class RemoteControlTest
{
    public static void main(String[] args)
    {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        Light light = new Light();
 
        // we can change command dynamically
        remote.setCommand(new LightOnCommand(light));
        remote.buttonWasPressed();
        remote.setCommand(new LightOffCommand(light));
        remote.buttonWasPressed();
     }
}
```



### References
* [Command Design Pattern - Derek Banas](https://www.youtube.com/watch?v=7Pj5kAhVBlg)
* [Udemy - Java Design Patterns & SOLID Design Principles](https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects/)
* [https://www.geeksforgeeks.org/command-pattern/](https://www.geeksforgeeks.org/command-pattern/)
