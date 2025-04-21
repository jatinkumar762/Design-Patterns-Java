package com.jatin.gymchain.dtos;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Booking {
    private String id;
    private String gymId;
    private String gymClassId;
    private String customerId;
    private Date bookingTime;

    public Booking(String customerId, String gymId, String classId){
        this.id = UUID.randomUUID().toString();
        this.gymId = gymId;
        this.customerId =customerId;
        this.gymClassId = classId;
        this.bookingTime = new Date();
    }
}
