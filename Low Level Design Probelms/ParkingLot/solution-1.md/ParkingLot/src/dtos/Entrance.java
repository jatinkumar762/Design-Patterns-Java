package dtos;

import service.TicketDispenser;

public class Entrance {
    private String id;
    private TicketDispenser ticketDispenser;

    public Entrance(String id){
        this.id = id;
        ticketDispenser = TicketDispenser.getInstance();
    }

    public String getId() {
        return id;
    }

    public Ticket getTicket(Vehicle vehicle, ParkingLot parkingLot){

        if (parkingLot.isFull()) {
            System.out.println("Parking lot is full!");
            return null;
        }

        ParkingSpot spot = parkingLot.assignSpot(vehicle);
        if (spot == null) {
            System.out.println("No available spot for this vehicle type");
            return null;
        }

        return ticketDispenser.generateTicket(vehicle, spot);
    }
}
