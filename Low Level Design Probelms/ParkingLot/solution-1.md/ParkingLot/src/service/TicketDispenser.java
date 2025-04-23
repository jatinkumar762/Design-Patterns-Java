package service;

import dtos.ParkingSpot;
import dtos.Ticket;
import dtos.Vehicle;

public class TicketDispenser {

    private static TicketDispenser instance = null;

    private TicketDispenser(){
    }

    public static TicketDispenser getInstance(){
        if(instance == null){
            instance = new TicketDispenser();
        }
        return instance;
    }

    public Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        return new Ticket(vehicle, parkingSpot);
    }
}
