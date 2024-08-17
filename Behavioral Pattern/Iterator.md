
#### Introduction

* Iterator allows a way to access elements/children of an aggregate object in sequence while hiding the actual internal data structure used.
* Java language iterators are implementations of this design pattern.
* Iterators are stateful, meaning an iterator object remembers its position while iterating

#### Key Components of the Iterator Design Pattern

1. **Iterator:** An interface that declares the operations for iterating over elements.

2. **Concrete Iterator:** A class that implements the Iterator interface and provides the implementation for iterating over elements.

3. **Aggregate:** An interface that declares the operations for **creating an iterator.**

4. **Concrete Aggregate:** A class that implements the Aggregate interface and **provides the implementation for creating an iterator.**



#### Example-1

**Step 1:** Define the Iterator Interface

```java
interface Iterator<T> {
    boolean hasNext();
    T next();
}
```

**Step 2:** Create Concrete Iterator Class

```java
class BookIterator implements Iterator<Book> {
    private Book[] books;
    private int position;

    public BookIterator(Book[] books) {
        this.books = books;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < books.length && books[position] != null;
    }

    @Override
    public Book next() {
        return books[position++];
    }
}
```

**Step 3:** Define the Aggregate Interface

```java
//Declares the operation for creating an iterator.
interface Aggregate<T> {
    Iterator<T> createIterator();
}
```

**Step 4:** Create Concrete Aggregate Class

```java
//Implements the Aggregate interface and provides the implementation for creating a BookIterator.
class BookCollection implements Aggregate<Book> {
    private Book[] books;
    private int index;

    public BookCollection(int size) {
        books = new Book[size];
        index = 0;
    }

    public void addBook(Book book) {
        if (index < books.length) {
            books[index++] = book;
        }
    }

    @Override
    public Iterator<Book> createIterator() {
        return new BookIterator(books);
    }
}
```

**Step 5:** Define the Book Class

```java
class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
```

**Step 6:** Use the Iterator Pattern

```java
public class IteratorPatternExample {
    public static void main(String[] args) {
        BookCollection collection = new BookCollection(5);
        collection.addBook(new Book("Design Patterns"));
        collection.addBook(new Book("Effective Java"));
        collection.addBook(new Book("Clean Code"));

        Iterator<Book> iterator = collection.createIterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            System.out.println("Book: " + book.getTitle());
        }
    }
}
```

#### Implement Iterator
* We start by defining Iterator interface
  * Iterator has methods to check whether there is an element available in sequence &to get that element
* We then implement the Iterator in a class. This is typically an inner class in our concrete aggregate. Making it an inner class makes it easy to access internal data structures

#### Example-2

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

#### Real-world Examples in Java

1. **Java's java.util.Iterator:**

The Java Collections Framework provides an Iterator interface that is used to iterate over collections such as List, Set, and Map.


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