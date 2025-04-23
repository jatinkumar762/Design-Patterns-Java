package service;

import dtos.*;

import java.util.Map;

public class ParkingLotService {

    private final ParkingLot parkingLot;

    public ParkingLotService(){
        parkingLot = ParkingLot.getInstance();
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public boolean addParkingFloor(String id, int compactCount, int largeCount, int handicappedCount, int motorCycleCount, int electricCount) {

        parkingLot.getParkingFloorList().add(new ParkingFloor(id, compactCount, largeCount, handicappedCount, motorCycleCount, electricCount));

        int count = compactCount + largeCount + handicappedCount + motorCycleCount + electricCount;

        parkingLot.setCapacity(parkingLot.getCapacity() + count);

        return true;
    }

    public boolean addEntrance(String id){
        parkingLot.getEntranceList().add(new Entrance(id));
        return true;
    }

    public boolean addExit(String id){
        parkingLot.getExitList().add(new Exit(id));
        return true;
    }

    public Entrance getEntrance(String id) {
        for (Entrance entrance : parkingLot.getEntranceList()) {
            if (entrance.getId().equals(id)) {
                return entrance;
            }
        }
        return null;
    }

    public Exit getExit(String id) {
        for (Exit exit : parkingLot.getExitList()) {
            if (exit.getId().equals(id)) {
                return exit;
            }
        }
        return null;
    }

    public ParkingSpot getSpot(Vehicle vehicle){

        for(ParkingFloor parkingFloor : parkingLot.getParkingFloorList()){
            ParkingSpot spot = parkingFloor.assignSpot(vehicle);

            if(spot!=null){
                return spot;
            }
        }

        return null;
    }
}
