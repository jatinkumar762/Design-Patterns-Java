package service;

import dtos.CardPayment;
import dtos.CashPayment;
import dtos.Payment;
import dtos.Ticket;
import enums.PaymentType;

import java.util.HashMap;
import java.util.Map;

public class PaymentProcessor {

    private static PaymentProcessor instance = null;

    private PaymentProcessor(){
    }

    public static PaymentProcessor getInstance(){

        if(instance == null){
            instance = new PaymentProcessor();
        }
        return instance;
    }

    public boolean processPayment(Ticket ticket, PaymentType paymentType, String...paymentInfo) {
        // In a real system, this would interact with payment UI
        // For simplicity, we'll assume cash payment is always successful

        Payment payment;
        switch (paymentType){
            case CASH -> payment = new CashPayment(ticket.getAmount());
            case CREDIT_CARD -> payment = new CardPayment(ticket.getAmount(), paymentInfo[0], paymentInfo[1]);
            default -> {
                return false;
            }
        }

        return payment.processPayment();
    }
}
