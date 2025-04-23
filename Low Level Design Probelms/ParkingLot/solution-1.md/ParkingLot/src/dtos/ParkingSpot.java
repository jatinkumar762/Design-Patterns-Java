package dtos;

import enums.ParkingSpotType;

public class ParkingSpot {
    private String id;
    private ParkingSpotType parkingSpotType;
    private boolean free;
    private Vehicle vehicle;
    private String parkingFloorId;

    public ParkingSpot(String id) {
        this.id = id;
        this.free = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpotType getParkingSpotType() {
        return parkingSpotType;
    }

    public void setParkingSpotType(ParkingSpotType parkingSpotType) {
        this.parkingSpotType = parkingSpotType;
    }

    public String getParkingFloorId() {
        return parkingFloorId;
    }

    public void setParkingFloorId(String parkingFloorId) {
        this.parkingFloorId = parkingFloorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean removeVehicle() {
        if (free) return false;
        this.vehicle = null;
        free = true;
        return true;
    }
}
