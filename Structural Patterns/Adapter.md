
### Introduction
* its used so that two unrelated interfaces can work together. 
* The object, that joins these unrelated interfaces, is called an Adapter.

#### Use Case
* We have an existing object which provides the functionality that client needs. But client code can't use this object because it expects an object with different interface.
* Using adapter design pattern we make this existing object work with client by adapting the object to client's expected interface.

#### Java Example

* The main use of this pattern is when a class that you need to use doesn’t meet the requirements of an interface.
* BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  1. System.in is static instance of InputStream, reads the data from the console in bytes stream.
  2. BufferedReader, reads a character stream.
  3. System.in provides byte stream where BufferedReader expects character stream. How they will work together?
  4. ideal situation to put a adapter in between two incompatible interfaces.
  5. InputStreamReader does exactly this thing and works adapter between System.in and BufferedReader.

#### Implementation

```java
/**
 * 
 * Interface expected by client
 *
 */
interface Target {
	void operation();
}

/**
 * 
 * our existing class providing
 * needed functionality
 */
interface Adaptee {
	void myOperation();
}

/**
 * 
 * Adapts existing functionality to 
 * target interface
 * 
 * Using composition instead 
 * of Inheritance
 */
class ObjectAdapter implements Target {

	Adaptee adaptee;

	ObjectAdapter(Adaptee adaptee) {
		this.adaptee = adaptee;
	}

	public void operation() {
		adaptee.myOperation();
	}
} 
```

#### Example - 1

```java
/**
 * 
 * An existing class used in our system
 * Adaptee
 * 
 */
public class Employee {
	private String fullName;
	private String jobTitle;
	private String officeLocation;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
}

/*
 * Target interface required
 * by client
 */
public interface Customer {
	String getName();
	String getDesignation();
	String getAddress();
}


/*
 * An object Adapter
 * Using composition to translate interface
 * 
 */
public class EmployeeObjectAdapter implements Customer {

	private Employee employee;
	
	EmployeeObjectAdapter(Employee employee){
		this.employee = employee;
	}
	
	@Override
	public String getName() {
		return employee.getFullName();
	}

	@Override
	public String getDesignation() {
		return employee.getJobTitle();
	}

	@Override
	public String getAddress() {
		return employee.getOfficeLocation();
	}

}

/*
* Client knows Customer interface
*/
public class BusinessCardDesigner {
	public static String designCard(Customer customer) {
		String card = "";
		card += customer.getName();
		card += customer.getDesignation();
		card += customer.getAddress();
		return card;
	}
}

public class AdapterDemo {
	public static void main(String args[]) {
		Employee emp = new Employee();
		emp.setFullName("Karan");
		emp.setJobTitle("Analyst");
		emp.setOfficeLocation("Phonepe, Bangalore");
		
		EmployeeObjectAdapter employeeObjectAdapter = new EmployeeObjectAdapter(emp);
		
		String card = BusinessCardDesigner.designCard(employeeObjectAdapter);
		
		System.out.println(card);
	}
}
```

#### Example-2

**Step 1: Define the Target Interface**

* Defines the interface that the client expects.

```java
interface MediaPlayer {
    void play(String audioType, String fileName);
}
```

**Step 2: Define the Adaptee Interface**

* Defines an existing interface that needs adapting.

```java
interface AdvancedMediaPlayer {
    void playVlc(String fileName);
    void playMp4(String fileName);
}

class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file. Name: " + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        // Do nothing
    }
}

class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        // Do nothing
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. Name: " + fileName);
    }
}
```

**Step 3: Create the Adapter Class**

* Implements the Target interface and translates requests from the Target interface to the Adaptee.

```java
class MediaAdapter implements MediaPlayer {
    AdvancedMediaPlayer advancedMediaPlayer;

    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer = new VlcPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMediaPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMediaPlayer.playMp4(fileName);
        }
    }
}
```

**Step 4: Create the Concrete Class Implementing the Target Interface**

```java
class AudioPlayer implements MediaPlayer {
    MediaAdapter mediaAdapter;

    @Override
    public void play(String audioType, String fileName) {
        // Inbuilt support to play mp3 music files
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file. Name: " + fileName);
        } 
        // MediaAdapter is providing support to play other file formats
        else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}
```

**Step 5: Use the Adapter Pattern**

```java
public class AdapterPatternExample {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "song.mp3");
        audioPlayer.play("mp4", "video.mp4");
        audioPlayer.play("vlc", "movie.vlc");
        audioPlayer.play("avi", "movie.avi");
    }
}
```

#### Real-world Examples in Java

1. **Java's java.util.Arrays#asList Method**

* The asList method in the Arrays class provides an adapter from an array to a List.

```java
import java.util.Arrays;
import java.util.List;

public class ArraysAsListExample {
    public static void main(String[] args) {
        String[] array = {"a", "b", "c"};
        List<String> list = Arrays.asList(array);

        System.out.println("List: " + list);
    }
}
```

2.  **Java's java.io.InputStreamReader and java.io.OutputStreamWriter**

* The InputStreamReader and OutputStreamWriter classes in Java act as adapters between byte streams (InputStream/OutputStream) and character streams (Reader/Writer).

```java
import java.io.*;

public class StreamAdapterExample {
    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("input.txt");
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```



### Useful Links
* [howtodoinjava - Adapter Design Pattern in Java](https://howtodoinjava.com/design-patterns/structural/adapter-design-pattern-in-java/)