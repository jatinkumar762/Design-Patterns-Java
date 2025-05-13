package app.models;

public class ElevatorSystem {
    private static ElevatorSystem instance;
    private ElevatorController controller;

    private ElevatorSystem(int numElevators) {
        // Using smart scheduler by default
        this.controller = new ElevatorController(numElevators, new SmartScheduler());
    }

    public static synchronized ElevatorSystem getInstance(int numElevators) {

        synchronized (ElevatorSystem.class) {
            if (instance == null) {
                instance = new ElevatorSystem(numElevators);
            }
        }
        return instance;
    }


    public void requestElevator(ExternalRequest request) {
        controller.handleRequest(request);
    }

    public void sendInternalRequest(int elevatorId, InternalRequest request) {
        controller.handleInternalRequest(elevatorId, request);
    }

    public Elevator getElevatorStatus(int elevatorId) {
        return controller.getElevators().get(elevatorId - 1);
    }
}
