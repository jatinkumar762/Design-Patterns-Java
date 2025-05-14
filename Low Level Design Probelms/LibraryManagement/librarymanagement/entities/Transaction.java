package librarymanagement.entities;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private String bookItemId;
    private String memberId;
    private Date issueDate;
    private Date returnDate;
    private String fineId;
    private TransactionStatus status;


}
