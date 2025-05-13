package app.models;

import app.enums.Direction;

public class ExternalRequest extends ElevatorRequest{

    private Direction direction;

    public ExternalRequest(int floor, Direction direction) {
        super(floor);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
