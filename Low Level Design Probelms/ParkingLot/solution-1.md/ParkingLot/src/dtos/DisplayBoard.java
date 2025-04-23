package dtos;

import enums.ParkingSpotType;

import java.util.HashMap;
import java.util.Map;

public class DisplayBoard {
    private Map<ParkingSpotType, Integer> freeSpots;

    public DisplayBoard() {
        freeSpots = new HashMap<>();
        for (ParkingSpotType type : ParkingSpotType.values()) {
            freeSpots.put(type, 0);
        }
    }

    public void update(ParkingSpotType type, int count) {
        freeSpots.put(type, count);
    }

    public void show() {
        System.out.println("Free Parking Spots:");
        for (Map.Entry<ParkingSpotType, Integer> entry : freeSpots.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
