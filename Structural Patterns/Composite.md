* allows to compose objects into tree-like structures to represent part-whole hierarchies.


#### Key Components of the Composite Design Pattern

1. **Component:**

* declares the interface for objects in the composition
* including the operations that must be implemented by leaf and composite objects.

2. **Leaf:**

* Represents leaf objects in the composition. A leaf has no children.

3. **Composite:**

* Represents a component that can have children
* It implements the child-related operations in the Component interface.

#### Example

* Let's consider an example where we have an organizational structure with Employee objects. 
* An Employee can be either an individual contributor (Leaf) or a manager who manages other employees (Composite).

**Step 1: Define the Component Interface**

```java
import java.util.List;

interface Employee {
    void showEmployeeDetails();
}
```

**Step 2: Create Leaf Class**

```java
// Represents leaf objects in the composition.
class Developer implements Employee {
    private String name;
    private long empId;
    private String position;

    public Developer(long empId, String name, String position) {
        this.empId = empId;
        this.name = name;
        this.position = position;
    }

    @Override
    public void showEmployeeDetails() {
        System.out.println(empId + " " + name + " " + position);
    }
}
```

**Step 3: Create Composite Class**

*  It can have children, adds or removes them, and implements the showEmployeeDetails method to show details of all its subordinates.

```java
//Represents a composite object in the composition.
import java.util.ArrayList;
import java.util.List;

class Manager implements Employee {
    private String name;
    private long empId;
    private String position;
    private List<Employee> subordinates;

    public Manager(long empId, String name, String position) {
        this.empId = empId;
        this.name = name;
        this.position = position;
        this.subordinates = new ArrayList<>();
    }

    public void add(Employee emp) {
        subordinates.add(emp);
    }

    public void remove(Employee emp) {
        subordinates.remove(emp);
    }

    @Override
    public void showEmployeeDetails() {
        System.out.println(empId + " " + name + " " + position);
        for (Employee emp : subordinates) {
            emp.showEmployeeDetails();
        }
    }
}
```

**Step 4: Use the Composite Pattern**

```java
//CLient
//Uses the composite structure to create a part-whole hierarchy
public class CompositePatternExample {
    public static void main(String[] args) {
        Developer dev1 = new Developer(100, "John Doe", "Pro Developer");
        Developer dev2 = new Developer(101, "Jane Doe", "Developer");
        
        Manager engManager = new Manager(200, "Mike Johnson", "Engineering Manager");
        engManager.add(dev1);
        engManager.add(dev2);
        
        Developer dev3 = new Developer(102, "Mary Smith", "Developer");
        
        Manager generalManager = new Manager(300, "Bill Gates", "General Manager");
        generalManager.add(dev3);
        generalManager.add(engManager);
        
        generalManager.showEmployeeDetails();
    }
}
```

#### Real-world Examples in Java

1. **Java's java.awt.Container and java.awt.Component**

* The AWT and Swing libraries in Java use the Composite pattern with Container and Component classes. 
* A Container can contain other components, including other containers.

```java
import java.awt.*;
import javax.swing.*;

public class CompositePatternAWTExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Composite Pattern Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");
        JButton button4 = new JButton("Button 4");
        
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
```

---

## Composite Design Pattern in Simple Terms

The Composite Design Pattern lets you treat individual objects and groups of objects (composites) in the same way. It's like dealing with a single item or a box containing multiple items - you interact with both the same way.

### Real-world Analogy

Imagine a file system:
- A **file** is a single object
- A **folder** is a composite that can contain files or other folders
- You can perform operations (like "display") on both files and folders in the same way

### Java Implementation

Here's a simple implementation:

```java
// Component interface - common for both leaf and composite
interface FileSystemItem {
    void display(String indent);
}

// Leaf - represents individual objects
class File implements FileSystemItem {
    private String name;
    
    public File(String name) {
        this.name = name;
    }
    
    @Override
    public void display(String indent) {
        System.out.println(indent + "File: " + name);
    }
}

// Composite - represents groups of objects
class Folder implements FileSystemItem {
    private String name;
    private List<FileSystemItem> children = new ArrayList<>();
    
    public Folder(String name) {
        this.name = name;
    }
    
    public void add(FileSystemItem item) {
        children.add(item);
    }
    
    @Override
    public void display(String indent) {
        System.out.println(indent + "Folder: " + name);
        for (FileSystemItem item : children) {
            item.display(indent + "  ");
        }
    }
}

// Usage
public class CompositeDemo {
    public static void main(String[] args) {
        Folder root = new Folder("Root");
        
        File file1 = new File("Document.txt");
        File file2 = new File("Image.jpg");
        
        Folder subFolder = new Folder("Subfolder");
        File file3 = new File("Note.txt");
        
        root.add(file1);
        root.add(file2);
        root.add(subFolder);
        subFolder.add(file3);
        
        root.display("");
    }
}
```

#### Output:
```
Folder: Root
  File: Document.txt
  File: Image.jpg
  Folder: Subfolder
    File: Note.txt
```

### Key Points:
1. **Component Interface**: Common interface for both leaf and composite (`FileSystemItem`)
2. **Leaf**: Represents individual objects (`File`)
3. **Composite**: Contains leaves or other composites (`Folder`)
4. **Transparent Treatment**: You can call the same methods (`display()`) on both files and folders

### When to Use:
- When you need to represent part-whole hierarchies
- When you want clients to treat individual objects and compositions uniformly
- Common in GUI systems, file systems, organizational structures

The pattern simplifies client code because it doesn't need to know whether it's dealing with a single object or a group of objects.