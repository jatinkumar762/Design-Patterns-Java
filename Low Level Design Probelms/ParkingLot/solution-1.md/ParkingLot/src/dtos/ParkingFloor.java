package dtos;

import enums.ParkingSpotType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingFloor {
    private final String id;
    private Map<ParkingSpotType, List<ParkingSpot>> parkingSpotTypeListMap;
    private DisplayBoard displayBoard;

    public ParkingFloor(String id, int compactCount, int largeCount, int handicappedCount, int motorCycleCount, int electricCount){
        this.id = id;
        this.displayBoard = new DisplayBoard();

        parkingSpotTypeListMap = new HashMap<>();

        parkingSpotTypeListMap.put(ParkingSpotType.COMPACT, createSpots(ParkingSpotType.COMPACT, compactCount));
        parkingSpotTypeListMap.put(ParkingSpotType.LARGE, createSpots(ParkingSpotType.LARGE, largeCount));
        parkingSpotTypeListMap.put(ParkingSpotType.HANDICAPPED, createSpots(ParkingSpotType.LARGE, handicappedCount));
        parkingSpotTypeListMap.put(ParkingSpotType.MOTORCYCLE, createSpots(ParkingSpotType.LARGE, motorCycleCount));
        parkingSpotTypeListMap.put(ParkingSpotType.ELECTRIC, createSpots(ParkingSpotType.LARGE, electricCount));

        updateDisplayBoard();

    }

    private void updateDisplayBoard() {
        for (ParkingSpotType type : parkingSpotTypeListMap.keySet()) {
            int count = (int) parkingSpotTypeListMap.get(type).stream().filter(ParkingSpot::isFree).count();
            displayBoard.update(type, count);
        }
    }

    private List<ParkingSpot> createSpots(ParkingSpotType type, int count) {
        List<ParkingSpot> spotList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ParkingSpot spot;
            switch (type) {
                case COMPACT:
                    spot = new ParkingSpot(id + "-C" + i);
                    break;
                case LARGE:
                    spot = new ParkingSpot(id + "-L" + i);
                    break;
                case HANDICAPPED:
                    spot = new ParkingSpot(id + "-H" + i);
                    break;
                case MOTORCYCLE:
                    spot = new ParkingSpot(id + "-M" + i);
                    break;
                case ELECTRIC:
                    spot = new ParkingSpot(id + "-E" + i);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid parking spot type");
            }
            spotList.add(spot);
        }
        return spotList;
    }

    public ParkingSpot assignSpot(Vehicle vehicle){
        ParkingSpotType parkingSpotType = getSpotTypeForVehicle(vehicle);

        List<ParkingSpot> parkingSpotList = parkingSpotTypeListMap.get(parkingSpotType);

        for(ParkingSpot parkingSpot : parkingSpotList){

            if(parkingSpot.isFree()){
                parkingSpot.setVehicle(vehicle);
                parkingSpot.setFree(false);
                parkingSpot.setParkingSpotType(parkingSpotType);
                parkingSpot.setParkingFloorId(id);
                return parkingSpot;
            }
        }

        return null;
    }

    private ParkingSpotType getSpotTypeForVehicle(Vehicle vehicle) {
        switch (vehicle.getVehicleType()) {
            case CAR:
                return ParkingSpotType.COMPACT;
            case TRUCK:
            case VAN:
                return ParkingSpotType.LARGE;
            case MOTORCYCLE:
                return ParkingSpotType.MOTORCYCLE;
            case ELECTRIC:
                return ParkingSpotType.ELECTRIC;
            default:
                return null;
        }
    }

    public boolean freeSpot(ParkingSpot spot) {
        if (spot.removeVehicle()) {
            updateDisplayBoard();
            return true;
        }
        return false;
    }
}
