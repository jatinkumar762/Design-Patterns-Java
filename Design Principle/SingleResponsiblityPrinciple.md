
* THERE SHOULD NEVER BE MORE THAN ONE REASON FOR A CLASS TO CHANGE

#### Main.java

```java
package com.company.singleresp;

import java.io.IOException;

public class Main {

	private static final String VALID_USER_JSON = "{\"name\": \"Randy\", \"email\": \"randy@email.com\", \"address\":\"110 Sugar lane\"}";
	
	private static final String INVALID_USER_JSON = "{\"name\": \"Sam\", \"email\": \"sam@email\", \"address\":\"111 Sugar lane\"}";

	public static void main(String[] args) throws IOException {
		UserController controller = new UserController();
		
		String response = controller.createUser(VALID_USER_JSON);	
		if(!response.equalsIgnoreCase("SUCCESS")) {
			System.err.println("Failed");
		}
		System.out.println("Valid JSON received response: "+response);
		response = controller.createUser(INVALID_USER_JSON);
		if(!response.equalsIgnoreCase("ERROR")) {
			System.err.println("Failed");
		}
		System.out.println("Invalid JSON received response: "+response);
	}

}
```

#### Store.java
```java
package com.company.singleresp;

import java.util.HashMap;
import java.util.Map;

//Stores data in memory
public class Store {

    private static final Map<String, User> STORAGE = new HashMap<>();

    public void store(User user) {
        synchronized(STORAGE) {
            STORAGE.put(user.getName(), user);
        }
    }

    public User getUser(String name) {
        synchronized(STORAGE) {
            return STORAGE.get(name);
        }
    }
}
```

#### User.java
```java
package com.company.singleresp;

//User
public class User {

    private String name;

    private String email;

    private String address;

    public User() {

    }

    public User(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", address=" + address + "]";
	}

    
}
```

#### UserPersistenceService.java
```java
package com.company.singleresp;

//A separate class for handling persistence 
public class UserPersistenceService {

	private Store store = new Store();
	
	public void saveUser(User user) {
		store.store(user);
	}
}
```

#### UserValidator.java
```java
package com.company.singleresp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//A separate class for handling validation of User
public class UserValidator {

	public boolean validateUser(User user) {
		return isValidUser(user);
	}
	

    //Validates the user object
    private boolean isValidUser(User user) {
        if(!isPresent(user.getName())) {
            return false;
        }
        user.setName(user.getName().trim());

        if(!isValidAlphaNumeric(user.getName())) {
            return false;
        }
        if(user.getEmail() == null || user.getEmail().trim().length() == 0) {
            return false;
        }
        user.setEmail(user.getEmail().trim());
        if(!isValidEmail(user.getEmail())) {
            return false;
        }
        return true;
    }
    
    //Simply checks if value is null or empty..
    private boolean isPresent(String value) {
        return value != null && value.trim().length() > 0;
    }
    //check string for special characters
    private boolean isValidAlphaNumeric(String value) {
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]"); 
        Matcher matcher = pattern.matcher(value);
        return !matcher.find();
    }
    //check string for valid email address
    private boolean isValidEmail(String value) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); 
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

}
```

#### ✅ UserController.java
```java
package com.company.singleresp;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

//Handles incoming JSON requests that work on User
public class UserController {

    private UserPersistenceService persistenceService = new UserPersistenceService();
    
    //Create a new user
    public String createUser(String userJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userJson, User.class);

        UserValidator validator = new UserValidator();
        boolean valid = validator.validateUser(user);
        
        if(!valid) {
            return "ERROR";
        }

        persistenceService.saveUser(user);
        
        return "SUCCESS";
    } 

}
```

---

### 🔴 Incorrect Implementation

#### 🚫 UserController.java

```java
package com.company.singleresp;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

//Handles incoming JSON requests that work on User resource/entity
public class UserController {
	//Store used by controller
    private Store store = new Store();
    
    //Create a new user
    public String createUser(String userJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        User user = mapper.readValue(userJson, User.class);

        if(!isValidUser(user)) {
            return "ERROR";
        }

        store.store(user);
        
        return "SUCCESS";
    } 

    //Validates the user object
    private boolean isValidUser(User user) {
        if(!isPresent(user.getName())) {
            return false;
        }
        user.setName(user.getName().trim());

        if(!isValidAlphaNumeric(user.getName())) {
            return false;
        }
        if(user.getEmail() == null || user.getEmail().trim().length() == 0) {
            return false;
        }
        user.setEmail(user.getEmail().trim());
        if(!isValidEmail(user.getEmail())) {
            return false;
        }
        return true;
    }
    
    //Simply checks if value is null or empty..
    private boolean isPresent(String value) {
        return value != null && value.trim().length() > 0;
    }
    //check string for special characters
    private boolean isValidAlphaNumeric(String value) {
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]"); 
        Matcher matcher = pattern.matcher(value);
        return !matcher.find();
    }
    //check string for valid email address - this is not for prod. 
    //Just for demo. This fails for lots of valid emails.
    private boolean isValidEmail(String value) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); 
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

}
```

#### reference

* [Java Design Patterns & SOLID Design Principles](https://www.udemy.com/course/design-patterns-in-java-concepts-hands-on-projects)


