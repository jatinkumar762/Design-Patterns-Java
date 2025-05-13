package app.models;

import java.util.List;

public class SimpleScheduler implements ElevatorScheduler{

    @Override
    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest request) {
        // Simple round-robin scheduling
        for (Elevator elevator : elevators) {
            if (elevator.getState() instanceof IdleState) {
                return elevator;
            }
        }
        return elevators.get(0); // Fallback to first elevator
    }
}
