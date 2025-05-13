package app.models;

public class MovingUpState implements ElevatorState{

    @Override
    public void move(Elevator elevator, int destinationFloor) {
        if (elevator.getCurrentFloor() < destinationFloor) {
            // Continue moving up
            elevator.move(destinationFloor);
        } else {
            elevator.setState(new IdleState());
            elevator.stop();
        }
    }

    @Override
    public void stop(Elevator elevator) {
        elevator.setState(new IdleState());
    }
}
