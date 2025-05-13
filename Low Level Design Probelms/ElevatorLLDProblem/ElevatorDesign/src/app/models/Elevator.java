package app.models;

import app.enums.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator {
    private int id;
    private int currentFloor;
    private ElevatorState state;
    //min heap
    private PriorityQueue<Integer> upRequests;
    //max heap
    private PriorityQueue<Integer> downRequests;
    private List<Integer> currentJobs;
    private volatile boolean processing = false;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.state = new IdleState();
        this.upRequests = new PriorityQueue<>();
        this.downRequests = new PriorityQueue<>(Collections.reverseOrder());
        this.currentJobs = new ArrayList<>();
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }

    // Thread-safe request addition
    public void addRequest(int floor, Direction direction) {
        synchronized (this) {
            if (direction == Direction.UP) {
                upRequests.add(floor);
                //Collections.sort(upRequests);
            } else {
                downRequests.add(floor);
                //Collections.sort(downRequests, Collections.reverseOrder());
            }
            notifyAll(); // Notify processing thread
        }
    }

    public void move(int destinationFloor) {
        System.out.println("Elevator " + id + " moving from " + currentFloor + " to " + destinationFloor);
        // Simulate movement
        while (currentFloor != destinationFloor) {
            synchronized (this) {
                if (currentFloor < destinationFloor) {
                    currentFloor++;
                } else {
                    currentFloor--;
                }

                //checkIntermediateRequests();
            }
            System.out.println("Elevator " + id + " at floor " + currentFloor);
            try {
                Thread.sleep(1000); // Simulate time between floors
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("Elevator " + id + " arrived at floor " + currentFloor);
        state.stop(this);
    }

    /**
     * we can use disk seek algorithm concept here - LOOK Algorithms
     * watch - shrayansh jain elevator lld video
     *
     */
    public void startProcessing() {
        Thread processorThread = new Thread(() -> {
            while (true) {
                try {
                    synchronized (this) {
                        // Wait if no requests
                        while (upRequests.isEmpty() && downRequests.isEmpty()) {
                            wait();
                        }

                        processing = true;

                        /*
                        // Process up requests first
                        if (!upRequests.isEmpty()) {
                            int nextFloor = upRequests.poll();
                            state.move(this, nextFloor);
                        }
                        // Then process down requests
                        else if (!downRequests.isEmpty()) {
                            int nextFloor = downRequests.poll();
                            state.move(this, nextFloor);
                        }
                         */

                        // Process requests in current direction
                        if (state instanceof MovingUpState) {
                            processUpRequests();
                        }
                        else if (state instanceof MovingDownState) {
                            processDownRequests();
                        }
                        else { // Idle - decide direction based on requests
                            if (!upRequests.isEmpty()) {
                                state = new MovingUpState();
                                processUpRequests();
                            }
                            else if (!downRequests.isEmpty()) {
                                state = new MovingDownState();
                                processDownRequests();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } finally {
                    processing = false;
                }
            }
        });
        processorThread.setDaemon(true);
        processorThread.start();
    }

    private void processUpRequests() {
        // Find next appropriate UP request
        Integer nextFloor = upRequests.poll();

        if (nextFloor != null) {
            move(nextFloor);
            upRequests.remove(nextFloor);
        } else {
            // No more UP requests in current direction - switch to DOWN if needed
            if (!downRequests.isEmpty()) {
                state = new MovingDownState();
            }
        }
    }

    private void processDownRequests() {
        // Find next appropriate DOWN request
        Integer nextFloor = downRequests.poll();

        if (nextFloor != null) {
            move(nextFloor);
        } else {
            // No more DOWN requests in current direction - switch to UP if needed
            if (!upRequests.isEmpty()) {
                state = new MovingUpState();
            }
        }
    }

    private void checkIntermediateRequests() {
        // Check if we should stop at current floor for any requests
        if (state instanceof MovingUpState && upRequests.contains(currentFloor)) {
            System.out.println("Stopping at floor " + currentFloor + " for UP request");
            upRequests.remove(Integer.valueOf(currentFloor));
        }
        else if (state instanceof MovingDownState && downRequests.contains(currentFloor)) {
            System.out.println("Stopping at floor " + currentFloor + " for DOWN request");
            downRequests.remove(Integer.valueOf(currentFloor));
        }
    }

    public void stop() {
        state.stop(this);
    }
}
