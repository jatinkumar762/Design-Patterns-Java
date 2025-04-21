# Service Layer Implementation for Gym Chain

Here's a refactored version with proper service layer separation, following domain-driven design principles while maintaining thread safety:

## 1. Domain Models (unchanged from previous)

```java
// Same Gym, GymClass, and Booking model classes as before
// (with getters/setters and basic properties)
```

## 2. Repository Layer (Data Access)

```java
public interface GymRepository {
    Gym findById(String gymId);
    void save(Gym gym);
    void delete(String gymId);
}

public interface GymClassRepository {
    GymClass findById(String classId);
    List<GymClass> findByGymId(String gymId);
    void save(GymClass gymClass);
    void delete(String classId);
}

public interface BookingRepository {
    Booking findById(String bookingId);
    List<Booking> findByCustomerId(String customerId);
    void save(Booking booking);
    void delete(String bookingId);
}

// Thread-safe implementations using ConcurrentHashMap
public class InMemoryGymRepository implements GymRepository {
    private final Map<String, Gym> gyms = new ConcurrentHashMap<>();
    
    @Override
    public Gym findById(String gymId) {
        return gyms.get(gymId);
    }
    
    @Override
    public void save(Gym gym) {
        gyms.put(gym.getId(), gym);
    }
    
    @Override
    public void delete(String gymId) {
        gyms.remove(gymId);
    }
}
```

## 3. Service Layer (Business Logic)

```java
@Service
public class GymService {
    private final GymRepository gymRepository;
    private final GymClassRepository gymClassRepository;
    
    public GymService(GymRepository gymRepository, GymClassRepository gymClassRepository) {
        this.gymRepository = gymRepository;
        this.gymClassRepository = gymClassRepository;
    }
    
    public String createGym(String name, String location, int maxAccommodation) {
        Gym gym = new Gym(name, location, maxAccommodation);
        gymRepository.save(gym);
        return gym.getId();
    }
    
    public void removeGym(String gymId) {
        Gym gym = gymRepository.findById(gymId);
        if (gym == null) {
            throw new IllegalArgumentException("Gym not found");
        }
        
        gym.getLock().lock();
        try {
            // Remove all classes first
            gymClassRepository.findByGymId(gymId).forEach(class -> {
                gymClassRepository.delete(class.getId());
            });
            gymRepository.delete(gymId);
        } finally {
            gym.getLock().unlock();
        }
    }
    
    public String addClassToGym(String gymId, String classType, 
                               int maxLimit, String startTime, String endTime) {
        Gym gym = gymRepository.findById(gymId);
        if (gym == null) {
            throw new IllegalArgumentException("Gym not found");
        }
        
        gym.getLock().lock();
        try {
            // Check gym capacity
            int currentOccupancy = gymClassRepository.findByGymId(gymId)
                .stream()
                .mapToInt(GymClass::getCurrentOccupancy)
                .sum();
            
            if (currentOccupancy + maxLimit > gym.getMaxAccommodation()) {
                throw new IllegalStateException("Exceeds gym capacity");
            }
            
            GymClass gymClass = new GymClass(gymId, classType, maxLimit, startTime, endTime);
            gymClassRepository.save(gymClass);
            return gymClass.getId();
        } finally {
            gym.getLock().unlock();
        }
    }
}
```

```java
@Service
public class BookingService {
    private final GymRepository gymRepository;
    private final GymClassRepository gymClassRepository;
    private final BookingRepository bookingRepository;
    
    public BookingService(GymRepository gymRepository, 
                         GymClassRepository gymClassRepository,
                         BookingRepository bookingRepository) {
        this.gymRepository = gymRepository;
        this.gymClassRepository = gymClassRepository;
        this.bookingRepository = bookingRepository;
    }
    
    public String bookClass(String customerId, String gymId, String classId) {
        Gym gym = gymRepository.findById(gymId);
        GymClass gymClass = gymClassRepository.findById(classId);
        
        if (gym == null || gymClass == null) {
            throw new IllegalArgumentException("Gym or class not found");
        }
        
        gym.getLock().lock();
        try {
            gymClass.getLock().lock();
            try {
                if (gymClass.isCustomerBooked(customerId)) {
                    throw new IllegalStateException("Customer already booked this class");
                }
                
                if (gymClass.getCurrentOccupancy() >= gymClass.getMaxLimit()) {
                    throw new IllegalStateException("Class is full");
                }
                
                Booking booking = new Booking(customerId, gymId, classId);
                gymClass.bookClass(customerId);
                bookingRepository.save(booking);
                return booking.getId();
            } finally {
                gymClass.getLock().unlock();
            }
        } finally {
            gym.getLock().unlock();
        }
    }
    
    public List<Booking> getCustomerBookings(String customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
    
    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        
        Gym gym = gymRepository.findById(booking.getGymId());
        GymClass gymClass = gymClassRepository.findById(booking.getClassId());
        
        if (gym == null || gymClass == null) {
            throw new IllegalStateException("Associated gym or class not found");
        }
        
        gym.getLock().lock();
        try {
            gymClass.getLock().lock();
            try {
                if (gymClass.cancelBooking(booking.getCustomerId())) {
                    bookingRepository.delete(bookingId);
                }
            } finally {
                gymClass.getLock().unlock();
            }
        } finally {
            gym.getLock().unlock();
        }
    }
}
```

## 4. Controller Layer (API Endpoints)

```java
@RestController
@RequestMapping("/api/gyms")
public class GymController {
    private final GymService gymService;
    
    public GymController(GymService gymService) {
        this.gymService = gymService;
    }
    
    @PostMapping
    public ResponseEntity<String> createGym(@RequestBody GymCreationRequest request) {
        String gymId = gymService.createGym(
            request.getName(),
            request.getLocation(),
            request.getMaxAccommodation()
        );
        return ResponseEntity.ok(gymId);
    }
    
    @DeleteMapping("/{gymId}")
    public ResponseEntity<Void> deleteGym(@PathVariable String gymId) {
        gymService.removeGym(gymId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{gymId}/classes")
    public ResponseEntity<String> addClass(
            @PathVariable String gymId,
            @RequestBody ClassCreationRequest request) {
        String classId = gymService.addClassToGym(
            gymId,
            request.getClassType(),
            request.getMaxLimit(),
            request.getStartTime(),
            request.getEndTime()
        );
        return ResponseEntity.ok(classId);
    }
}
```

## 5. Configuration

```java
@Configuration
public class AppConfig {
    @Bean
    public GymRepository gymRepository() {
        return new InMemoryGymRepository();
    }
    
    @Bean
    public GymClassRepository gymClassRepository() {
        return new InMemoryGymClassRepository();
    }
    
    @Bean
    public BookingRepository bookingRepository() {
        return new InMemoryBookingRepository();
    }
    
    @Bean
    public GymService gymService() {
        return new GymService(gymRepository(), gymClassRepository());
    }
    
    @Bean
    public BookingService bookingService() {
        return new BookingService(gymRepository(), gymClassRepository(), bookingRepository());
    }
}
```

## Key Benefits of This Structure:

1. **Separation of Concerns**:
   - Clear division between data access (Repository), business logic (Service), and presentation (Controller)

2. **Thread Safety Maintained**:
   - Object-level locks still protect critical sections
   - ConcurrentHashMap for repository storage

3. **Testability**:
   - Services can be easily mocked for unit testing
   - Clear interfaces between layers

4. **Scalability**:
   - Repository implementations could be swapped for database versions
   - Services can be distributed if needed

5. **Maintainability**:
   - Business logic concentrated in service layer
   - Clear flow of dependencies

This architecture provides a solid foundation that could be extended with:
- Database persistence
- Caching layer
- Notification services
- More complex business rules
- Monitoring and logging