package dtos;

import enums.PaymentStatus;

import java.time.LocalDateTime;

public abstract class Payment {
    private double amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime paidAt;

    public Payment(double amount) {
        this.paymentStatus = PaymentStatus.PENDING;
        this.paidAt = LocalDateTime.now();
        this.amount = amount;
    }

    public abstract boolean processPayment();

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
