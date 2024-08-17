
### Introduction
* its used so that two unrelated interfaces can work together. 
* The object, that joins these unrelated interfaces, is called an Adapter.

#### Use Case
* We have an existing object which provides the functionality that client needs. But client code can't use this object because it expects an object with different interface.
* Using adapter design pattern we make this existing object work with client by adapting the object to client's expected interface.

#### Java Example

* The main use of this pattern is when a class that you need to use doesn’t meet the requirements of an interface.
* BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  1. System.in is static instance of InputStream, reads the data from the console in bytes stream.
  2. BufferedReader, reads a character stream.
  3. System.in provides byte stream where BufferedReader expects character stream. How they will work together?
  4. ideal situation to put a adapter in between two incompatible interfaces.
  5. InputStreamReader does exactly this thing and works adapter between System.in and BufferedReader.

#### Implementation

```java
/**
 * 
 * Interface expected by client
 *
 */
interface Target {
	void operation();
}

/**
 * 
 * our existing class providing
 * needed functionality
 */
interface Adaptee {
	void myOperation();
}

/**
 * 
 * Adapts existing functionality to 
 * target interface
 * 
 * Using composition instead 
 * of Inheritance
 */
class ObjectAdapter implements Target {

	Adaptee adaptee;

	ObjectAdapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	public void operation() {
		adaptee.myOperation();
	}
} 
```

#### Example - 1

```java
/**
 * 
 * An existing class used in our system
 * Adaptee
 * 
 */
public class Employee {
	private String fullName;
	private String jobTitle;
	private String officeLocation;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
}

/*
 * Target interface required
 * by client
 */
public interface Customer {
	String getName();
	String getDesignation();
	String getAddress();
}


/*
 * An object Adapter
 * Using composition to translate interface
 * 
 */
public class EmployeeObjectAdapter implements Customer {

	private Employee employee;
	
	EmployeeObjectAdapter(Employee employee){
		this.employee = employee;
	}
	
	@Override
	public String getName() {
		return employee.getFullName();
	}

	@Override
	public String getDesignation() {
		return employee.getJobTitle();
	}

	@Override
	public String getAddress() {
		return employee.getOfficeLocation();
	}

}

/*
* Client knows Customer interface
*/
public class BusinessCardDesigner {
	public static String designCard(Customer customer) {
		String card = "";
		card += customer.getName();
		card += customer.getDesignation();
		card += customer.getAddress();
		return card;
	}
}

public class AdapterDemo {
	public static void main(String args[]) {
		Employee emp = new Employee();
		emp.setFullName("Karan");
		emp.setJobTitle("Analyst");
		emp.setOfficeLocation("Phonepe, Bangalore");
		
		EmployeeObjectAdapter employeeObjectAdapter = new EmployeeObjectAdapter(emp);
		
		String card = BusinessCardDesigner.designCard(employeeObjectAdapter);
		
		System.out.println(card);
	}
}
```






### Useful Links
* [howtodoinjava - Adapter Design Pattern in Java](https://howtodoinjava.com/design-patterns/structural/adapter-design-pattern-in-java/)