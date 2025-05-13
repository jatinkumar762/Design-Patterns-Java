package app.models;

import app.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private final ElevatorScheduler scheduler;
    private final List<Elevator> elevators;

    public ElevatorController(int numElevators, ElevatorScheduler scheduler) {
        this.scheduler = scheduler;
        this.elevators = new ArrayList<>();

        for (int i = 0; i < numElevators; i++) {
            Elevator elevator = new Elevator(i + 1);
            elevator.startProcessing();
            elevators.add(elevator);
        }
    }

    public void handleRequest(ExternalRequest request) {
        Elevator elevator = scheduler.selectElevator(elevators, request);
        elevator.addRequest(request.getFloor(), request.getDirection());
        //new Thread(() -> elevator.processRequests()).start();
    }

    public void handleInternalRequest(int elevatorId, InternalRequest request) {
        Elevator elevator = elevators.get(elevatorId - 1);
        Direction direction = elevator.getCurrentFloor() < request.getFloor() ? Direction.UP : Direction.DOWN;
        elevator.addRequest(request.getFloor(), direction);
        //new Thread(() -> elevator.processRequests()).start();
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}
