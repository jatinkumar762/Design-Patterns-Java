package dtos;

import enums.PaymentStatus;

public class CashPayment extends Payment {

    public CashPayment(double amount){
        super(amount);
    }

    @Override
    public boolean processPayment() {
        setPaymentStatus(PaymentStatus.PAID);
        return true;
    }
}
