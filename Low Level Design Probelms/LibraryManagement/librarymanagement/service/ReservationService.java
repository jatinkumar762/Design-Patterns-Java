package librarymanagement.service;

import librarymanagement.dto.Member;
import librarymanagement.entities.Book;
import librarymanagement.entities.BookItem;
import librarymanagement.entities.Reservation;
import librarymanagement.enums.BookStatus;
import librarymanagement.enums.ReservationStatus;

import java.util.List;
import java.util.Optional;

public class ReservationService {

    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private BookItemRepository bookItemRepository;
    private ReservationRepository reservationRepository;
    private NotificationService notificationService;

    public Reservation reserveBook(String bookId, String){

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Check if book has available copies
        List<BookItem> availableCopies = bookItemRepository
                .findByBookIdAndStatus(bookId, BookStatus.AVAILABLE);

        if (!availableCopies.isEmpty()) {
            throw new RuntimeException("Book is available for checkout, no need to reserve"));
        }

        Optional<Reservation> existingReservation = reservationRepository
                .findByBookISBNAndMemberMemberIdAndStatus(bookId, memberId, ReservationStatus.WAITING);

        if (existingReservation.isPresent()) {
            throw new RuntimeException("Member already has a pending reservation for this book"));
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.randomUUID().toString());
        reservation.setBook(book);
        reservation.setMember(member);
        reservation.setCreationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.WAITING);

        return reservationRepository.save(reservation);

    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.WAITING) {
            throw new RuntimeException("Only waiting reservations can be cancelled"));
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public void fulfillReservation(String reservationId, String bookBarcode) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new RuntimeException("Reservation is not in pending state"));
        }

        BookItem bookItem = bookItemRepository.findById(bookBarcode)
                .orElseThrow(() -> new RuntimeException("Book item not found"));

        if (bookItem.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book item is not available"));
        }

        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);

        notificationService.sendReservationFulfilledNotification(reservation);
    }
}
