package com.jatin.gymchain.service;

import com.jatin.gymchain.dtos.Booking;
import com.jatin.gymchain.dtos.Gym;
import com.jatin.gymchain.dtos.GymClass;
import com.jatin.gymchain.enums.ClassType;

import java.util.*;
import java.util.stream.Collectors;

public class GymService {

    private Map<String, Gym> gymMap = new HashMap<>();
    private Map<String, Booking> bookingMap = new HashMap<>();
    private Map<String, List<String>> customerBookings = new HashMap<>();;

    public String addGym(String name, String location, int maxAccomodation){

        Gym gym = new Gym(name, location, maxAccomodation);

        gymMap.put(gym.getId(), gym);

        return gym.getId();
    }

    public boolean removeGym(String gymId){

        Gym gym = gymMap.get(gymId);
        if( gym == null){
            return false;
        }

        gym.getLock().lock();
        try {

            // Remove all bookings for classes in this gym
            gym.getClasses().values().forEach(gymClass -> {
                gymClass.getLock().lock();
                try {
                    removeClass(gymClass.getGymId(), gymClass.getId());
                } finally {
                    gymClass.getLock().unlock();
                }
            });

            gymMap.remove(gymId);
            return true;

        } finally {
            gym.getLock().unlock();
        }
    }

    public String addClass(String gymId, String classType, int maxLimit, String startTime, String endTime) {
        Gym gym = gymMap.get(gymId);
        if( gym == null){
            return null;
        }

        gym.getLock().lock();
        try {

            int currentOccupancy = gym.getCurrentOccupancy();

            if(currentOccupancy + maxLimit > gym.getMaxAccommodation()){
                return null;
            }

            gym.setCurrentOccupancy(currentOccupancy + maxLimit);

            GymClass gymClass = new GymClass(gymId, ClassType.valueOf(classType), maxLimit, startTime, endTime);

            gym.getClasses().put(gymClass.getGymId(), gymClass);

            return gymClass.getGymId();

        } finally {
            gym.getLock().unlock();
        }
    }

    public boolean removeClass(String gymId, String classId) {

        Gym gym = gymMap.get(gymId);
        if (gym == null) {
            return false;
        }

        gym.getLock().lock();

        try {

            GymClass gymClass = gym.getClasses().get(classId);
            if (gymClass == null) {
                return false;
            }

            gymClass.getLock().lock();

            try {

                // Remove all bookings for this class
                gymClass.getBookedCustomerIds().forEach(customerId -> {
                    String bookingId = findBookingId(customerId, gymId, classId);
                    if (bookingId != null) {
                        bookingMap.remove(bookingId);
                        customerBookings.get(customerId).remove(bookingId);
                    }
                });

                gym.getClasses().remove(classId);
                return true;

            } finally {
                gymClass.getLock().unlock();
            }

        } finally {
            gym.getLock().unlock();
        }

    }

    private String findBookingId(String customerId, String gymId, String classId) {
        List<String> bookingIds = customerBookings.get(customerId);
        if (bookingIds == null) {
            return null;
        }

        for (String bookingId : bookingIds) {
            Booking booking = bookingMap.get(bookingId);
            if (booking != null && booking.getGymId().equals(gymId) && booking.getGymClassId().equals(classId)) {
                return bookingId;
            }
        }
        return null;
    }

    public String bookClass(String customerId, String gymId, String classId) {

        Gym gym = gymMap.get(gymId);
        if (gym == null) {
            return null;
        }

        gym.getLock().lock();
        try {

            GymClass gymClass = gym.getClasses().get(classId);
            if (gymClass == null) {
                return null;
            }

            gymClass.getLock().lock();
            try {

                if(gymClass.bookClass(customerId)){
                    Booking booking = new Booking(customerId, gymId, classId);
                    bookingMap.put(booking.getId(), booking);
                    customerBookings.computeIfAbsent(customerId, k -> new ArrayList<>()).add(booking.getId());
                    return booking.getId();
                }
                return null;

            } finally {
                gymClass.getLock().unlock();
            }

        } finally {
            gym.getLock().unlock();
        }

    }

    public List<Booking> getAllBookings(String customerId) {
        List<String> bookingIds = customerBookings.getOrDefault(customerId, Collections.emptyList());
        return bookingIds.stream()
                .map(bookingMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = bookingMap.get(bookingId);
        if (booking == null) {
            return false;
        }

        Gym gym = gymMap.get(booking.getGymId());
        if (gym == null) {
            return false;
        }

        gym.getLock().lock();
        try {
            GymClass gymClass = gym.getClasses().get(booking.getGymClassId());
            if (gymClass == null) {
                return false;
            }

            gymClass.getLock().lock();
            try {
                if (gymClass.cancelBooking(booking.getCustomerId())) {
                    bookingMap.remove(bookingId);
                    customerBookings.get(booking.getCustomerId()).remove(bookingId);
                    return true;
                }
                return false;
            } finally {
                gymClass.getLock().unlock();
            }
        } finally {
            gym.getLock().unlock();
        }
    }

}
