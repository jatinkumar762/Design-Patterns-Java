* Problems solved by Builder Design Pattern
* Implementation
* Summary (Pros and Cons)

### Problems solved by Builder Design Pattern

* Creation of complex objects (some properties are mandatory or optional)
* We don't need to creeate mutiple constructors for different combination of properties
* Helps with immutable classes (Define object which once created, never change their value)
* Less need for exposing setters, if we use setter method then object will be mutable

### Implementation

#### Builder Example

```java
package creational.builder;

class Mobile {
	public final String modelName;
	private final String companyName;
	private final Integer ramCapacity;
	private final String processorName;

	private Mobile(MobileBuilder mobileBuilder) {
		this.modelName = mobileBuilder.modelName;
		this.companyName = mobileBuilder.companyName;
		this.ramCapacity = mobileBuilder.ramCapacity;
		this.processorName = mobileBuilder.processorName;
	}

	@Override
	public String toString() {
		return "Model: " + this.modelName + ", companyName: " + this.companyName + ", ramCapacity: " + this.ramCapacity
				+ "GB, processorName: " + this.processorName;
	}
	
	public static MobileBuilder builder() {
		return new MobileBuilder();
	}

	public static class MobileBuilder {

		private String modelName;
		private String companyName;
		private Integer ramCapacity;
		private String processorName;

		public MobileBuilder modelName(String modelName) {
			this.modelName = modelName;
			return this;
		}

		public MobileBuilder companyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public MobileBuilder ramCapacity(Integer ramCapacity) {
			this.ramCapacity = ramCapacity;
			return this;
		}

		public MobileBuilder processorName(String processorName) {
			this.processorName = processorName;
			return this;
		}

		public Mobile build() {
			Mobile mobile = new Mobile(this);
			validateMobileObject(mobile);
			return mobile;
		}

		private void validateMobileObject(Mobile mobile) {
			if (mobile.modelName == null || mobile.processorName == null)
				throw new RuntimeException("wrong parameters");
		}

	}
}

public class MobileBuilderExample {
	public static void main(String args[]) {
		Mobile mobile = Mobile.builder().modelName("Iphone 12").companyName("Apple").ramCapacity(8)
				.processorName("Intel").build();
		System.out.println(mobile);
		
		//Object is immutable
		//mobile.modelName = "Iphone 13";
		
	}
}
```

#### Setter Example

```java
package creational.builder.setter;


class Mobile {
	private String modelName;
	private String companyName;
	private Integer ramCapacity;
	private String processorName;
	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @param ramCapacity the ramCapacity to set
	 */
	public void setRamCapacity(Integer ramCapacity) {
		this.ramCapacity = ramCapacity;
	}
	/**
	 * @param processorName the processorName to set
	 */
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	
	@Override
	public String toString() {
		return "Mobile [modelName=" + modelName + ", companyName=" + companyName + ", ramCapacity=" + ramCapacity
				+ ", processorName=" + processorName + "]";
	}
		
}

public class MobileWithSetters {
	public static void main(String args[]) {
		Mobile mobile = new Mobile();
		mobile.setModelName("Iphone 12");
		mobile.setCompanyName("Apple");
		mobile.setProcessorName("Intel");
		mobile.setRamCapacity(8);
		
		System.out.println(mobile);
		
		//object is mutable
		mobile.setRamCapacity(6);
		
		System.out.println(mobile);
		
	}
}
```

### Summary

#### Pros
* Good way to handle complexity
* Easy to implement, we have to just use innner static class

#### Cons
* Class instance returned is immutable
* Uses inner static class
* Sometimes number of lines of code can be huge

### Resources
* [https://howtodoinjava.com/design-patterns/creational/builder-pattern-in-java/](https://howtodoinjava.com/design-patterns/creational/builder-pattern-in-java/)
* [https://github.com/anomaly2104/coding-recipies/tree/master/src/main/java/com/uditagarwal/builder_pattern](https://github.com/anomaly2104/coding-recipies/tree/master/src/main/java/com/uditagarwal/builder_pattern)
* [Udit Agarwal](https://www.youtube.com/watch?v=6Wi2XZeAf-Q)
* [SudoCode](https://www.youtube.com/watch?v=4ff_KZdvJn8)


