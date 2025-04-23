package dtos;

import enums.PaymentStatus;

public class CardPayment extends Payment {
    private String cardNumber;
    private String name;

    public CardPayment(double amount, String cardNumber, String name){
        super(amount);
        this.cardNumber = cardNumber;
        this.name = name;
    }

    @Override
    public boolean processPayment() {
        setPaymentStatus(PaymentStatus.PAID);
        return true;
    }
}
