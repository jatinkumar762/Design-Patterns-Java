package librarymanagement.entities;

import librarymanagement.enums.PaymentStatus;
import java.util.Date;

/**
 * one to one
 */
public class Fine {
    private String fineId;
    private double amount;
    private Date creationDate;
    private PaymentStatus paymentStatus;
    private Date paymentDate;
    private String transactionId; // loan id
}
