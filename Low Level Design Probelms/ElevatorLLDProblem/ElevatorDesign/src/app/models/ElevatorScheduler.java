package app.models;

import java.util.List;

public interface ElevatorScheduler {
    Elevator selectElevator(List<Elevator> elevators, ExternalRequest request);
}
