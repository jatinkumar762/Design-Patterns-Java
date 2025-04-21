package com.jatin.gymchain.dtos;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Data
public class Gym {
    private String id;
    private String name;
    private String location;
    private int maxAccommodation;
    private int currentOccupancy;
    private Map<String, GymClass> classes;
    private ReentrantLock lock;

    public Gym(String name, String location, int maxAccommodation){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.maxAccommodation = maxAccommodation;
        this.lock = new ReentrantLock();
        this.currentOccupancy = 0;
        classes = new HashMap<>();
    }
}