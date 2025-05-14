package librarymanagement.entities;

import librarymanagement.enums.ReservationStatus;

import java.util.Date;

public class Reservation {
    private String reservationId;
    private Date creationDate;
    private ReservationStatus status;
    private String bookItemId;
    private String memberId;
}
