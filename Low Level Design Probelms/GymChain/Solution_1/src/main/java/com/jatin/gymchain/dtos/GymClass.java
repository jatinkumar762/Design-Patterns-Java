package com.jatin.gymchain.dtos;

import com.jatin.gymchain.enums.ClassType;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class GymClass {
    private String id;
    private String gymId;
    private int maxLimit;
    private String startTime;
    private String endTime;
    private ClassType classType;
    private Set<String> bookedCustomerIds;
    private ReentrantLock lock;

    public GymClass(String gymId, ClassType classType, int maxLimit, String startTime, String endTime) {
        this.id = UUID.randomUUID().toString();
        this.gymId = gymId;
        this.classType = classType;
        this.maxLimit = maxLimit;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lock = new ReentrantLock();
    }

    public boolean bookClass(String customerId) {
        if (bookedCustomerIds.size() < maxLimit && !bookedCustomerIds.contains(customerId)) {
            bookedCustomerIds.add(customerId);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(String customerId) {
        return bookedCustomerIds.remove(customerId);
    }

}
