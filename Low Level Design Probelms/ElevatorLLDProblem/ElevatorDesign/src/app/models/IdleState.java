package app.models;

public class IdleState implements ElevatorState{

    @Override
    public void move(Elevator elevator, int destinationFloor) {

        if (elevator.getCurrentFloor() < destinationFloor) {
            elevator.setState(new MovingUpState());
        } else if (elevator.getCurrentFloor() > destinationFloor) {
            elevator.setState(new MovingDownState());
        }
        elevator.move(destinationFloor);
    }

    @Override
    public void stop(Elevator elevator) {
        //already in stop
    }
}
