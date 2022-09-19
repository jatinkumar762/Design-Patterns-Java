#### Intoduction

* When we want to enhance behaviour of our existing object dynamically as and when required
* Decorator wraps an object within itself and provides same interface as the wrapped object. So the client of original object does not need to change.
* A decorator provides alternative to subclassing for extending funcationality of existing classes.

#### Implementation Steps

* We start with out component.
  * Component defines interface needed or already used by client.
  * Concrete component implements the component.
  * We define our decorator. Decorator implements component & also needs reference to concrete component.
  * In decorator method we provide additional behaviour on top that provided by concrete component instance.
* Decorator can be abstract as well & depend on subclasses to provide functionality.

#### Implementation Example

```java
package structural.decorator;

//Base Interface or Component
public interface Message {
	String getContent();
}

/**
 * 
 * Concrete component
 * Object to be decorated
 */
public class TextMessage implements Message {

	String mssg;

	TextMessage(String mssg) {
		this.mssg = mssg;
	}

	@Override
	public String getContent() {
		return this.mssg;
	}
}

/**
 * 
 * Decorator.
 * Implements component interface
 */
public class UpperCaseMessage implements Message {
	Message mssg;

	UpperCaseMessage(Message mssg) {
		this.mssg = mssg;
	}
	
	@Override
	public String getContent() {
		return mssg.getContent().toUpperCase();
	}
}

/**
 * 
 * Decorator
 */
public class Base64EncodedMessage implements Message {
	Message mssg;

	Base64EncodedMessage(Message mssg) {
		this.mssg = mssg;
	}

	@Override
	public String getContent() {
		return Base64.getEncoder().encodeToString(mssg.getContent().getBytes());
	}
}

public class Client {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message txtMessage = new TextMessage("<Hello>, How are you?");
		System.out.println(txtMessage.getContent());

		Message decorator = new UpperCaseMessage(txtMessage);
		System.out.println(decorator.getContent());
		
		decorator = new Base64EncodedMessage(decorator);
		System.out.println(decorator.getContent());
	}
}
```

#### Implementation Considerations

* Pay attention to equals and hashcode methods of decorator. When using decorators, you have to decide if decorated object is equal to same instance without decorator.
* Decorator support recursive composition. Difficult to debug sometimes.

#### Design Considerations

* Decorators are more powerful & flexible than inheritance. Inheritance is static by definition but decorator allows you to dynamically compose behaviour using object at runtime. (At runtime we can decide which object is to include or not)
* Do not change meaning of original operation. It should add helpful small behaviour to object's original behaviour.

#### Example of a Decorator

* 