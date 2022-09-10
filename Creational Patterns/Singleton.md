
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


### Summary

#### Pros
* Easy to handle access to shared global resource
* Easy to implement and gurantees 1 instance

#### Cons
* Used with parameters and confused with factory
* Thread safety has to be ensured

### Resources
* [sudoCode - Story of Singleton Design Pattern](https://www.youtube.com/watch?v=EZDeEHXUf8w)


