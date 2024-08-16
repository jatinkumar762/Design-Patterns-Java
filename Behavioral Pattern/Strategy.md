* helps in selecting an algorithm's behavior at runtime
* This pattern promotes the Open/Closed Principle, allowing easy extension with new discount strategies without modifying existing code. 
* It also supports changing the strategy at runtime.

**Key Components**

1. **Strategy Interface:** This defines a common interface for all supported algorithms. Each algorithm implements this interface.

2. **Concrete Strategies:** These are the classes that implement the Strategy interface. Each concrete strategy provides a different implementation of the algorithm.

3. **Context:** This maintains a reference to a Strategy object and allows the strategy to be changed dynamically. The context does not know how the strategy works; it just invokes the strategy's method.

#### Example in Java

**Step 1: Define the Strategy Interface**

```java
// Strategy Interface
public interface DiscountStrategy {
    double applyDiscount(double price);
}
```

**Step 2: Implement Concrete Strategies**

```java
// Concrete Strategy for No Discount
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price;
    }
}

// Concrete Strategy for Seasonal Discount
public class SeasonalDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.9; // 10% discount
    }
}

// Concrete Strategy for Clearance Discount
public class ClearanceDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.5; // 50% discount
    }
}
```

**Step 3: Create the Context**

```java
// Context
public class PriceCalculator {
    private DiscountStrategy discountStrategy;

    public PriceCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculatePrice(double price) {
        return discountStrategy.applyDiscount(price);
    }
}
```

**Step 4: Use the Strategy Pattern**

```java
public class StrategyPatternDemo {
    public static void main(String[] args) {
        PriceCalculator calculator = new PriceCalculator(new NoDiscountStrategy());
        double price = 100.0;

        System.out.println("Original Price: " + price);
        System.out.println("Price with No Discount: " + calculator.calculatePrice(price));

        calculator.setDiscountStrategy(new SeasonalDiscountStrategy());
        System.out.println("Price with Seasonal Discount: " + calculator.calculatePrice(price));

        calculator.setDiscountStrategy(new ClearanceDiscountStrategy());
        System.out.println("Price with Clearance Discount: " + calculator.calculatePrice(price));
    }
}
```

#### Example-2 

**Step 1: Define the Strategy Interface**

```java
// Strategy Interface
public interface SortStrategy {
    void sort(int[] numbers);
}
```

**Step 2: Implement Concrete Strategies**

```java
// Concrete Strategy for Bubble Sort
public class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] numbers) {
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    // swap numbers[j] and numbers[j + 1]
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
        System.out.println("Bubble Sorted: " + Arrays.toString(numbers));
    }
}

// Concrete Strategy for Quick Sort
public class QuickSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] numbers) {
        quickSort(numbers, 0, numbers.length - 1);
        System.out.println("Quick Sorted: " + Arrays.toString(numbers));
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
```

**Step 3: Implement the Context Class**

```java
// Context Class
public class SortContext {
    private SortStrategy strategy;

    public void setSortStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] numbers) {
        strategy.sort(numbers);
    }
}
```

**Step 4: Use the Strategy Pattern**

```java
// Client Code
public class StrategyPatternDemo {
    public static void main(String[] args) {
        int[] numbers = {5, 2, 9, 1, 5, 6};

        SortContext context = new SortContext();

        // Using Bubble Sort Strategy
        context.setSortStrategy(new BubbleSortStrategy());
        context.sort(numbers);

        // Using Quick Sort Strategy
        context.setSortStrategy(new QuickSortStrategy());
        context.sort(numbers);
    }
}
```

**When to Use the Strategy Pattern**

* When you have multiple algorithms for a specific task and want to switch between them during runtime.
* When you want to avoid using conditional statements for selecting the appropriate algorithm.
* When you want to make the code more maintainable and extensible by separating algorithm implementation from the context.


#### References
* https://howtodoinjava.com/design-patterns/behavioral/strategy-design-pattern/
* https://www.geeksforgeeks.org/strategy-pattern-set-1/
* https://www.geeksforgeeks.org/strategy-pattern-set-2/