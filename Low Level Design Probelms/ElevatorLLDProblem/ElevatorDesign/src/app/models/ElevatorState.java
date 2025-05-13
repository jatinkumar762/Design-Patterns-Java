package app.models;

public interface ElevatorState {

    public void move(Elevator elevator, int destinationFloor);

    public void stop(Elevator elevator);
}
