# Collection-1

Here’s a list of **practical low-level design (LLD) problems** or examples that are commonly associated with each **design pattern**, helping you to implement and understand them better:

---

### **Creational Patterns**

1. **Factory Method**

   * **Example**: Notification System (`EmailNotification`, `SMSNotification`)
   * **Problem**: Design a system where different types of notifications can be sent without modifying the core logic.
   * [Notification Factory Example (GeeksforGeeks)](https://www.geeksforgeeks.org/factory-method-design-pattern-in-java/)

2. **Abstract Factory**

   * **Example**: UI Toolkit (`WindowsFactory`, `MacFactory`)
   * **Problem**: Create GUI elements for different OS platforms without changing client code.
   * [Abstract Factory UI Example (Baeldung)](https://www.baeldung.com/java-abstract-factory-pattern)

3. **Builder**

   * **Example**: Meal or Resume Builder
   * **Problem**: Construct a complex object (like a resume or meal combo) step by step.
   * [Builder Pattern Example (Refactoring Guru)](https://refactoring.guru/design-patterns/builder)

4. **Prototype**

   * **Example**: Cloneable Shape Objects
   * **Problem**: Clone objects (like UI components or game characters) to avoid expensive object creation.
   * [Prototype Shape Example (Refactoring Guru)](https://refactoring.guru/design-patterns/prototype)

5. **Singleton**

   * **Example**: Database Connection or Configuration Manager
   * **Problem**: Ensure a single instance of a logger or config object throughout the application.
   * [Singleton Pattern Example (Baeldung)](https://www.baeldung.com/java-singleton)

---

### **Structural Patterns**

6. **Adapter**

   * **Example**: Media Player supporting multiple formats
   * **Problem**: Convert an incompatible interface (e.g., `VLCPlayer`) to one the client expects (`MediaPlayer`).
   * [Adapter Media Player Example (Refactoring Guru)](https://refactoring.guru/design-patterns/adapter)

7. **Bridge**

   * **Example**: Shape and Color decoupling
   * **Problem**: Decouple abstraction (Shape) from implementation (Color).
   * [Bridge Pattern Example (Baeldung)](https://www.baeldung.com/java-bridge-pattern)

8. **Composite**

   * **Example**: File System
   * **Problem**: Represent part-whole hierarchy (folders containing files and subfolders).
   * [Composite File System Example (Refactoring Guru)](https://refactoring.guru/design-patterns/composite)

9. **Decorator**

   * **Example**: Coffee with various add-ons
   * **Problem**: Attach additional responsibilities (milk, sugar) to a beverage dynamically.
   * [Coffee Decorator Example (Baeldung)](https://www.baeldung.com/java-decorator-pattern)

10. **Facade**

    * **Example**: Hotel Booking System
    * **Problem**: Provide a simplified interface (`HotelFacade`) to a complex subsystem (`RoomService`, `Payment`, `Booking`).
    * [Facade Example (GeeksforGeeks)](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)

11. **Flyweight**

    * **Example**: Text Editor Character Caching
    * **Problem**: Reuse memory-heavy objects like characters or tree nodes in large-scale structures.
    * [Flyweight Character Example (Refactoring Guru)](https://refactoring.guru/design-patterns/flyweight)

12. **Proxy**

    * **Example**: Image Proxy Loader
    * **Problem**: Load images lazily via a proxy (`ImageProxy` instead of loading full `RealImage`).
    * [Proxy Image Loader Example (Baeldung)](https://www.baeldung.com/java-proxy-pattern)

---

### **Behavioral Patterns**

13. **Chain of Responsibility**

    * **Example**: Logging System (Error → Debug → Info)
    * **Problem**: Pass request along a chain of handlers until one handles it.
    * [Chain of Responsibility Logging Example (Refactoring Guru)](https://refactoring.guru/design-patterns/chain-of-responsibility)

14. **Observer**

    * **Example**: Stock Price Ticker or UI Listener
    * **Problem**: Notify multiple observers when an event occurs (e.g., stock price change).
    * [Observer Pattern Example (Baeldung)](https://www.baeldung.com/java-observer-pattern)

15. **Strategy**

    * **Example**: Payment Methods (CreditCard, PayPal)
    * **Problem**: Select a behavior (e.g., payment algorithm) at runtime.
    * [Strategy Pattern Example (Baeldung)](https://www.baeldung.com/java-strategy-pattern)

16. **Template Method**

    * **Example**: Game or Report Generation
    * **Problem**: Define a skeleton of an algorithm with steps that subclasses can override.
    * [Game Template Example (Refactoring Guru)](https://refactoring.guru/design-patterns/template-method)

17. **State**

    * **Example**: Vending Machine / TCP Connection
    * **Problem**: Change behavior based on object’s state (`Idle`, `Dispensing`, etc.).
    * [State Pattern Vending Machine Example (Baeldung)](https://www.baeldung.com/java-state-design-pattern)

18. **Command**

    * **Example**: Text Editor Undo-Redo
    * **Problem**: Encapsulate requests as objects, queue them, and allow undo/redo.
    * [Command Text Editor Example (Refactoring Guru)](https://refactoring.guru/design-patterns/command)

19. **Mediator**

    * **Example**: Chat Room
    * **Problem**: Centralize communication between components (e.g., chat participants).
    * [Mediator Chat Room Example (Baeldung)](https://www.baeldung.com/java-mediator-pattern)

20. **Iterator**

    * **Example**: Custom Collection (like Playlist or BookShelf)
    * **Problem**: Access elements of a collection sequentially without exposing internals.
    * [Iterator Pattern Example (Refactoring Guru)](https://refactoring.guru/design-patterns/iterator)

---

# Collection-2

Here are Low-Level Design (LLD) problem examples or links for each design pattern to help you understand their implementation:

---

### **Creational Patterns**
1. **Factory Method**  
   - **Problem**: Design a payment system where you need to create different payment methods (CreditCard, PayPal, UPI).  
   - **Example**: [Payment Gateway System](https://www.geeksforgeeks.org/design-payment-gateway-system/)  

2. **Abstract Factory**  
   - **Problem**: Design a UI toolkit that supports multiple themes (Light & Dark) with consistent buttons, text fields, and dialogs.  
   - **Example**: [Cross-Platform UI Components](https://refactoring.guru/design-patterns/abstract-factory)  

3. **Builder**  
   - **Problem**: Design a complex object like a `Computer` with optional components (RAM, SSD, GPU).  
   - **Example**: [Building a Custom Computer](https://www.baeldung.com/java-builder-pattern-freebuilder)  

4. **Prototype**  
   - **Problem**: Design a game where enemies (e.g., monsters) are cloned instead of created from scratch.  
   - **Example**: [Game Character Cloning](https://refactoring.guru/design-patterns/prototype)  

5. **Singleton**  
   - **Problem**: Design a logging system where only one instance should write logs to a file.  
   - **Example**: [Logger Class Implementation](https://www.geeksforgeeks.org/singleton-design-pattern/)  

---

### **Structural Patterns**
6. **Adapter**  
   - **Problem**: Integrate a legacy `XMLData` interface with a new `JSONProcessor`.  
   - **Example**: [XML to JSON Adapter](https://refactoring.guru/design-patterns/adapter)  

7. **Bridge**  
   - **Problem**: Design a drawing API that works across different rendering engines (Vector vs. Raster).  
   - **Example**: [Shape Rendering System](https://www.journaldev.com/1491/bridge-design-pattern-java)  

8. **Composite**  
   - **Problem**: Design a file system where directories and files are treated uniformly.  
   - **Example**: [File System Hierarchy](https://refactoring.guru/design-patterns/composite)  

9. **Decorator**  
   - **Problem**: Add toppings (milk, sugar) to a base coffee dynamically.  
   - **Example**: [Coffee Customization](https://www.geeksforgeeks.org/decorator-pattern/)  

10. **Facade**  
    - **Problem**: Simplify a complex home theater system (TV, Sound, Lights) with a single remote.  
    - **Example**: [Home Theater System](https://refactoring.guru/design-patterns/facade)  

11. **Flyweight**  
    - **Problem**: Optimize memory usage in a text editor by reusing character objects.  
    - **Example**: [Text Formatting](https://www.geeksforgeeks.org/flyweight-design-pattern/)  

12. **Proxy**  
    - **Problem**: Implement lazy loading for heavy database queries.  
    - **Example**: [Virtual Proxy for Images](https://refactoring.guru/design-patterns/proxy)  

---

### **Behavioral Patterns**
13. **Chain of Responsibility**  
    - **Problem**: Design a logging system where logs are handled by different loggers (INFO → DEBUG → ERROR).  
    - **Example**: [Multi-Level Logging](https://refactoring.guru/design-patterns/chain-of-responsibility)  

14. **Observer**  
    - **Problem**: Implement a stock market notifier that alerts subscribers when prices change.  
    - **Example**: [Stock Price Updates](https://www.geeksforgeeks.org/observer-pattern-set-1-introduction/)  

15. **Strategy**  
    - **Problem**: Design a navigation app with multiple route strategies (Fastest, Cheapest, Scenic).  
    - **Example**: [Route Planning](https://refactoring.guru/design-patterns/strategy)  

16. **Template Method**  
    - **Problem**: Define a skeleton for building houses (foundation → walls → roof) with customizable steps.  
    - **Example**: [House Construction Process](https://www.javatpoint.com/template-method-pattern)  

17. **State**  
    - **Problem**: Implement a vending machine with states (Idle, Processing, Dispensing).  
    - **Example**: [Vending Machine State Management](https://refactoring.guru/design-patterns/state)  

18. **Command**  
    - **Problem**: Design a remote control for smart home devices (undo/redo operations).  
    - **Example**: [Remote Control for Lights/Fans](https://www.geeksforgeeks.org/command-pattern/)  

19. **Mediator**  
    - **Problem**: Implement a chat room where users communicate via a central mediator.  
    - **Example**: [Chat Application](https://refactoring.guru/design-patterns/mediator)  

20. **Iterator**  
    - **Problem**: Traverse a custom collection (e.g., a playlist) without exposing its internal structure.  
    - **Example**: [Music Playlist Iterator](https://www.geeksforgeeks.org/iterator-pattern/)  

---

### **Additional Resources**
- [Refactoring Guru](https://refactoring.guru/design-patterns/) (Interactive examples)  
- [SourceMaking](https://sourcemaking.com/design_patterns) (Detailed case studies)  