#### Introduction
* We can notify multiple objects whenever an object changes state.
* This design pattern also known as publisher-subscriber or pub-sub
* We are defining one-to-maqny dependency between objects, where many objects are listening for state change of a single object, without tightly coupling all of them together.
* This pattern is often implemented where listener only gets notification that "something" has changed in the object's state. Listener query back to find out more information if needed. This makes it more generic as different listeners may be interested in different states.

#### Implementation Steps
* We define an interface for observer. Observer is usually a very simple interface and defines a method used by "subject" to notify about state change.
* Subject can be an interface if we are expecting our observers to listen to multiple objects or else subject can be any concrete class.
* Implementing subject means taking care of handling attach, detach of observers, notifying all registered observers & providing methods to provide state information requested by observers.
* Concrete observers uses a reference passed to them to call "subject" for getting more information about the state. If we are passing changed state in notify method then its not required.

#### Implementation Example

```java
import java.util.ArrayList;
import java.util.List;

//A concrete subject 
public class Order {

    private String id;

    private double shippingCost;
    //cost of items
    private double itemCost;

    private double discount;
    //no of items
    private int count;

    private List<OrderObserver> observers = new ArrayList<>();
    
    public Order(String id) {
        this.id = id;
    }
    
    public void attach(OrderObserver observer) {
    	observers.add(observer);
    }

    public void detach(OrderObserver observer) {
    	observers.remove(observer);
    }
    
    public double getTotal() {
        return itemCost - discount + shippingCost;
    }

    public void addItem(double price) {
        itemCost += price;
        count ++;
        observers.stream().forEach(o-> o.updated(this));
    }

    public int getCount() {
        return count;
    }

    public void setShippingCost(double cost) {
        this.shippingCost = cost;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getItemCost() {
        return itemCost;
    }

    @Override
    public String toString() {

        return "Order#"+id+"\nItem cost:"+itemCost+"\nNo. of items:"+count
                +"\nShipping cost:"+shippingCost+"\nDiscount:"+discount
                +"\nTotal:"+getTotal();
    }
}

//Abstract observer
public interface OrderObserver {
  void updated(Order order);
}

//Concrete observer
public class QuantityObserver implements OrderObserver {
	@Override
    public void updated(Order order) {
        int count = order.getCount();
        if(count <= 5) {
            order.setShippingCost(10);
        } else {
            order.setShippingCost(10 + (count - 5) * 1.5);
        }
    }
}

//Concrete observer
public class PriceObserver implements OrderObserver{

	@Override
	public void updated(Order order) {
		double cost = order.getItemCost();
		
		if(cost >= 500) {
			order.setDiscount(50);
		} else if(cost >= 200) {
			order.setDiscount(10);
		}
	}
}

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Order order = new Order("101");
    	PriceObserver price = new PriceObserver();
    	order.attach(price);
    	
    	QuantityObserver quant = new QuantityObserver();
    	order.attach(quant);
    	
    	order.addItem(50);
    	order.addItem(179);
    	
    	System.out.println(order);
	}

}
```


#### Implementation Consideration
* In some rare scenarios you may end with a circular update loop. i.e. - an update to observable's state results in notification being sent to a observer which then takes some action and that action results in state change of our observable,triggering another notificat on. Watch for these!
* An observer object can listen for changes in multiple subjects. It becomes quite easy the notification if subjects pass a reference to themselves in notification to observer.
* Performance can become an issue if number of observers are higher and if one or many of them need noticeable time to process notification. This can also cause pile up of pending notifications or missed notifications.

#### Design Considerations
* To reduce number of notifications sent on each state update, we can also have observers register for a specific property or event. This improves performance as on an event, subject notifies only the interested observers instead of all registered observers. (Seprate list for each event)

#### Drawbacks
* Also each update becomes expensive as no. of observers increase and we have one or more "slow" observers in the list.
* If observers call back the subject to find what changed then this can add up to quite a bit of overhead.

#### Summary
* Observer pattern allows to define one-to-many dependency between objects where many objects are interested in state change of a object.
* Observers register themselves with the subject which then notifies all registered observers if any state change occurs.
* In the notification sent to observers it is common to only send reference of subject instead of state values.Observers call the subject back for more information if needed.
* We can also register observers for a specific event only, resulting in improved performance of sending notifications in subject.
* This design pattern is also known as publisher-subscriber pattern.Java messaging uses this pattern but instead of registering with subject,listeners register with a JMS broker, which acts as a middleman.
