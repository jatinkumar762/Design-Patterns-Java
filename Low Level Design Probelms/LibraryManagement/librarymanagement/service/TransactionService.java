package librarymanagement.service;

import librarymanagement.dto.Member;
import librarymanagement.entities.BookItem;
import librarymanagement.entities.Fine;
import librarymanagement.entities.Reservation;
import librarymanagement.entities.Transaction;
import librarymanagement.enums.BookStatus;
import librarymanagement.enums.PaymentStatus;
import librarymanagement.enums.ReservationStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class TransactionService {

    private BookItemRepository bookItemRepository;
    private MemberRepository memberRepository;
    private MemberService memberService;
    private TransactionRepository transactionRepository;
    private NotificationService notificationService;
    private ReservationRepository reservationRepository;

    public Transaction checkoutBook(String memberId, String bookBarcode){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        BookItem bookItem = bookItemRepository.findById(bookBarcode)
                .orElseThrow(() -> new RuntimeException("Book item not found"));

        // Check if member can checkout more books
        if (!memberService.canCheckoutBooks(memberId)) {
            throw new RuntimeException("Member has reached maximum checkout limit");
        }

        // Check if book is available
        if (bookItem.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for checkout");
        }

        Transaction loan = new Transaction();
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setBookItem(bookItem);
        loan.setMember(member);
        loan.setCheckoutDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(10)); // 10 day limit

        // Update book status
        bookItem.setStatus(BookStatus.LOANED);
        bookItemRepository.save(bookItem);

        return transactionRepository.save(loan);
    }

    public BookItem returnBook(String bookBarcode) {

        BookItem bookItem = bookItemRepository.findById(bookBarcode)
                .orElseThrow(() -> new RuntimeException("Book item not found"));

        if (bookItem.getStatus() != BookStatus.LOANED) {
            throw new RuntimeException("Book is not currently on loan");
        }

        // Find active loan
        Transaction loan = transactionRepository.findByBookItemBarcodeAndReturnDateIsNull(bookBarcode)
                .orElseThrow(() -> new RuntimeException("No active loan found for this book"));

        // Update loan
        loan.setReturnDate(LocalDate.now());
        transactionRepository.save(loan);

        // Check for overdue and calculate fine
        if (LocalDate.now().isAfter(loan.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
            double fineAmount = daysOverdue * 1.0; // $1 per day

            Fine fine = new Fine();
            fine.setFineId(UUID.randomUUID().toString());
            fine.setAmount(fineAmount);
            fine.setCreationDate(LocalDate.now());
            fine.setPaymentStatus(PaymentStatus.UNPAID);
            fine.setTransactionId(loan.getId);

            fineRepository.save(fine);

            // Send overdue notification
            notificationService.sendOverdueNotification(loan);
        }

        bookItem.setStatus(BookStatus.AVAILABLE);
        bookItem = bookItemRepository.save(bookItem);

        // Process any reservations for this book
        processReservations(bookItem.getBook().getISBN());

        return bookItem;
    }

    private void processReservations(String ISBN) {
        List<Reservation> reservations = reservationRepository
                .findByBookISBNAndStatus(ISBN, ReservationStatus.WAITING);

        if (!reservations.isEmpty()) {
            Reservation firstReservation = reservations.get(0);
            firstReservation.setStatus(ReservationStatus.PENDING);
            reservationRepository.save(firstReservation);

            notificationService.sendReservationAvailableNotification(firstReservation);
        }
    }
}
