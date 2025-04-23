package dtos;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Parking Lot (Singleton)
public class ParkingLot {

    private static ParkingLot instance = null;
    private List<ParkingFloor> parkingFloorList;
    private List<Entrance> entranceList;
    private List<Exit> exitList;
    private int capacity;
    private int occupied;
    private DisplayBoard displayBoard;

    private ParkingLot(){
        parkingFloorList = new ArrayList<>();
        entranceList = new ArrayList<>();
        exitList = new ArrayList<>();
        this.capacity = 0;
        this.occupied = 0;
        this.displayBoard = new DisplayBoard();
    }

    public static ParkingLot getInstance(){
        if(Objects.isNull(instance)){
            instance = new ParkingLot();
        }
        return instance;
    }

    public List<ParkingFloor> getParkingFloorList() {
        return parkingFloorList;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public List<Exit> getExitList() {
        return exitList;
    }

    public List<Entrance> getEntranceList() {
        return entranceList;
    }

    public ParkingSpot assignSpot(Vehicle vehicle) {
        for (ParkingFloor floor : parkingFloorList) {
            ParkingSpot spot = floor.assignSpot(vehicle);
            if (spot != null) {
                occupied++;
                return spot;
            }
        }
        return null;
    }

    public boolean isFull(){
        return occupied > capacity;
    }
}
