
```java
package creational;

abstract class Chair{
	abstract public int getPrice(); 
	abstract public void setPrice(int price); 
	@Override
	public String toString() {
		return "ChairA with price: "+this.getPrice();
	}
}

class ChairA extends Chair{
	
	private int price;
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}	
}

class ChairB extends Chair{
	
	private int price;
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}


abstract class AbstractFactory{
	abstract Chair getChairInstance(); 
}

class FactoryA extends AbstractFactory{

	@Override
	Chair getChairInstance() {
		// TODO Auto-generated method stub
		return new ChairA();
	}
	
}

class FactoryB extends AbstractFactory{

	@Override
	Chair getChairInstance() {
		// TODO Auto-generated method stub
		return new ChairB();
	}
	
}


public class AbstractFactoryPattern {
	
	public static void main(String args[]) {
		
		AbstractFactory factoryA = new FactoryA();
		
		Chair chair = factoryA.getChairInstance();
		chair.setPrice(1000);
		
		System.out.println(chair);
		
		
	}

}
```