
- This pattern is useful when we have a superclass with multiple sub-classes and based on input, we need to return one of the sub-class.
- Super class can be an interface, abstract class or a normal java class.

### Pros and Cons

<table>
    <thead>
        <tr>
            <th>Pros</th>
            <th>Cons</th>
        </tr>
    </thead>
    <tr>
        <td>Avoid tight coupling</td>
	    <td>code becomes complicated when we introduce a lot of </br> new subclasses to implement the pattern</td>
    </tr>
    <tr>
        <td>Single Responsibility Principle. Product creation code </br>at one place in the program.</td>
    </tr>
    <tr>
        <td>Open/Closed Principle. We can introduce new types </br>of products into the program</td>
    </tr>
</table>


```java

package creational;

abstract class Computer {
	public abstract String getRAM();
	public abstract String getHDD();
	public abstract String getCPU();
	
	@Override
	public String toString() {
		return "RAM= "+this.getRAM()+", HDD="+this.getHDD()+", CPU="+this.getCPU();
	}
}


class PC extends Computer{
	
	private String ram;
	private String hdd;
	private String cpu;
	
	public PC(String ram, String hdd, String cpu) {
		super();
		this.ram = ram;
		this.hdd = hdd;
		this.cpu = cpu;
	}
	
	@Override
	public String getRAM() {
		// TODO Auto-generated method stub
		return ram;
	}
	@Override
	public String getHDD() {
		// TODO Auto-generated method stub
		return hdd;
	}
	@Override
	public String getCPU() {
		// TODO Auto-generated method stub
		return cpu;
	}
	
}


class Server extends Computer{
	
	private String ram;
	private String hdd;
	private String cpu;
	
	public Server(String ram, String hdd, String cpu) {
		super();
		this.ram = ram;
		this.hdd = hdd;
		this.cpu = cpu;
	}

	@Override
	public String getRAM() {
		// TODO Auto-generated method stub
		return ram;
	}

	@Override
	public String getHDD() {
		// TODO Auto-generated method stub
		return hdd;
	}

	@Override
	public String getCPU() {
		// TODO Auto-generated method stub
		return cpu;
	}
	
	
}

class ComputerFactory{
	
	public static Computer getInstance(String type, String ram, String hdd, String cpu) {
		
		if("PC".equalsIgnoreCase(type)) {
			return new PC(ram, hdd, cpu);
		}
		else if("Server".equalsIgnoreCase(type)) {
			return new Server(ram, hdd, cpu);
		}
		return null;
	}
}

public class FactoryMethod {

	public static void main(String args[]) {
		
		Computer pc = ComputerFactory.getInstance("pc","2 GB","500 GB","2.4 GHz");
		
		Computer server = ComputerFactory.getInstance("server","16 GB","1 TB","2.9 GHz");
		
		System.out.println("Factory PC Config::"+pc);
		System.out.println("Factory Server Config::"+server);
		
	}	
	
}

```
