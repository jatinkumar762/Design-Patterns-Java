package app.models;

import app.enums.Direction;

import java.util.List;

public class SmartScheduler implements ElevatorScheduler{
    @Override
    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest request) {

        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());

            if (elevator.getState() instanceof IdleState) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            } else if (elevator.getState() instanceof MovingUpState &&
                    request.getDirection() == Direction.UP &&
                    elevator.getCurrentFloor() <= request.getFloor()) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            } else if (elevator.getState() instanceof MovingDownState &&
                    request.getDirection() == Direction.DOWN &&
                    elevator.getCurrentFloor() >= request.getFloor()) {
                if (distance < minDistance) {
                    minDistance = distance;
                    bestElevator = elevator;
                }
            }
        }

        return bestElevator != null ? bestElevator : elevators.get(0);
    }
}
