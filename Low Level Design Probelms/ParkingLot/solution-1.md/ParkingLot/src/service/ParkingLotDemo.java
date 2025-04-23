package service;

import dtos.Entrance;
import dtos.Exit;
import dtos.Ticket;
import dtos.Vehicle;
import enums.VehicleType;

public class ParkingLotDemo {

    public static void main(String args[]){

        ParkingLotService parkingLotService = new ParkingLotService();

        parkingLotService.addParkingFloor("F1", 20, 10, 5, 10, 5);
        parkingLotService.addParkingFloor("F2", 20, 10, 5, 10, 5);

        parkingLotService.addEntrance("E1");
        parkingLotService.addEntrance("E2");
        parkingLotService.addExit("X1");
        parkingLotService.addExit("X2");

        Vehicle vehicle = new Vehicle("ABC123", VehicleType.CAR);
        Entrance entrance = parkingLotService.getEntrance("E1");

        Ticket ticket = entrance.getTicket(vehicle, parkingLotService.getParkingLot());

        if(ticket!=null){



            Exit exit = parkingLotService.getExit("X1");
            exit.processTicket(ticket);
        }

    }
}
