package dtos;

import enums.PaymentType;
import service.PaymentProcessor;

public class Exit {
    private String id;
    private PaymentProcessor paymentProcessor;

    public Exit(String id){
        this.id = id;
        this.paymentProcessor = PaymentProcessor.getInstance();
    }

    public String getId() {
        return id;
    }

    public boolean processTicket(Ticket ticket){

        if(ticket.isPaid()){
            System.out.println("Ticket already paid. Thank you!");
            return true;
        }

        System.out.println("Amount to pay: $" + ticket.calculateAmount());

        boolean paymentSuccess = paymentProcessor.processPayment(ticket, PaymentType.CASH);

        if (paymentSuccess) {
            ticket.getParkingSpot().removeVehicle();
            System.out.println("Payment successful. Thank you!");
            return true;
        }

        System.out.println("Payment failed. Please try again.");
        return false;
    }
}
