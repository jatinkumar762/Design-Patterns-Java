package dtos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private String ticketNumber;
    private LocalDateTime issueTime;
    private LocalDateTime paidAt;
    private double amount;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private boolean paid;

    public Ticket(Vehicle vehicle, ParkingSpot parkingSpot){
        ticketNumber = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        issueTime = LocalDateTime.now();
        this.paid = false;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(LocalDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double calculateAmount() {
        Duration duration = Duration.between(issueTime, LocalDateTime.now());
        long hours = duration.toHours();

        if (hours <= 1) return 4.0;
        if (hours <= 3) return 4.0 + (hours - 1) * 3.5;

        this.amount = 4.0 + 2 * 3.5 + (hours - 3) * 2.5;

        return amount;
    }
}
