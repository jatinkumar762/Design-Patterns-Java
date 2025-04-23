package dtos;

import enums.VehicleType;

public class Vehicle {
    private String licenceNumber;
    private VehicleType vehicleType;

    public Vehicle(String licenceNumber, VehicleType vehicleType) {
        this.licenceNumber = licenceNumber;
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }
}
