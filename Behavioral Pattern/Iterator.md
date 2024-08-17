
#### Introduction

* Iterator allows a way to access elements/children of an aggregate object in sequence while hiding the actual internal data structure used.
* Java language iterators are implementations of this design pattern.
* Iterators are stateful, meaning an iterator object remembers its position while iterating

#### Key Components of the Iterator Design Pattern

1. **Iterator:** An interface that declares the operations for iterating over elements.

2. **Concrete Iterator:** A class that implements the Iterator interface and provides the implementation for iterating over elements.

3. **Aggregate:** An interface that declares the operations for **creating an iterator.**

4. **Concrete Aggregate:** A class that implements the Aggregate interface and **provides the implementation for creating an iterator.**


#### Implement Iterator
* We start by defining Iterator interface
  * Iterator has methods to check whether there is an element available in sequence &to get that element
* We then implement the Iterator in a class. This is typically an inner class in our concrete aggregate. Making it an inner class makes it easy to access internal data structures

#### Implementation Example

```java
package behavioral.iterator;

public enum ThemeColor {
	RED, ORANGE, BLACK, WHITE;

	public static Iterator<ThemeColor> getIterator() {
		return new ThemeColorIterator();
	}

	private static class ThemeColorIterator implements Iterator<ThemeColor> {

		private int position;

		@Override
		public boolean hasNext() {
			return position < ThemeColor.values().length;
		}

		@Override
		public ThemeColor next() {
			return ThemeColor.values()[position++];
		}

	}
}

//Iterator interface allowing to iterate over
//values of an aggregate
public interface Iterator<T> {
	boolean hasNext();

	T next();
}

public class Client {
	public static void main(String[] args) {
		Iterator<ThemeColor> iter = ThemeColor.getIterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}
```

#### Implementation Consideration

* Detecting change to underlying data structure while some code is using an iterator is important to notify to the client because then our iterator may not work correctly.
* Having our iterator implementation as inner class makes it easy to access internal collection of aggregate objects.

#### Drawbacks
* Access to index during iteration is not readily available like we have in a for loop.
* Making modifications to the collection while someone is using an iterator often makes that iterator instance invalid as its state may not be valid.

#### Summary
* When we want to iterate or give sequential access to elements of aggregate object we can use iterator design pattern.
* Iterator needs access to internal data structure of aggregator to provide its functionality.This usually means it's quite common to have iterator implemented as inner class.
* Iterator allows the client code to check whether there is an element available to consume and give next available element.