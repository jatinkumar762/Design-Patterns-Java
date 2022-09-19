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







### References
* [Command Design Pattern - Derek Banas](https://www.youtube.com/watch?v=7Pj5kAhVBlg)
