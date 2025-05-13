package app.models;

public abstract class ElevatorRequest {

    //Accessible within the same package and subclasses (even in different packages)
    protected int floor;

    protected ElevatorRequest(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }
}
