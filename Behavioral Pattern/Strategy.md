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


#### References
* https://howtodoinjava.com/design-patterns/behavioral/strategy-design-pattern/
* https://www.geeksforgeeks.org/strategy-pattern-set-1/
* https://www.geeksforgeeks.org/strategy-pattern-set-2/