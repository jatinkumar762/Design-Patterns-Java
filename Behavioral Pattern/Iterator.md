
#### Introduction
* Iterator allows a way to access elements/children of an aggregate object in sequence while hiding the actual internal data structure used.
* Java language iterators are implementations of this design pattern.
* Iterators are stateful, meaning an iterator object remebers its position while iterating

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