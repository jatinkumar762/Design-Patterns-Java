package app;

import app.enums.Direction;
import app.models.ElevatorSystem;
import app.models.ExternalRequest;
import app.models.InternalRequest;

public class ElevatorAppDemo {

    public static void main(String args[]){

        ElevatorSystem system = ElevatorSystem.getInstance(1);

        // External requests (people pressing buttons on floors)
        system.requestElevator(new ExternalRequest(3, Direction.UP));
        system.requestElevator(new ExternalRequest(5, Direction.DOWN));
        system.requestElevator(new ExternalRequest(1, Direction.UP));

        // Internal requests (people inside elevators pressing buttons)
        try {
            Thread.sleep(2000); // Wait for elevators to start moving
            system.sendInternalRequest(1, new InternalRequest(7));
            system.sendInternalRequest(1, new InternalRequest(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
