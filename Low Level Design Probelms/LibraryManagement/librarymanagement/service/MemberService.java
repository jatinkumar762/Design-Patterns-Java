package librarymanagement.service;

import librarymanagement.dto.Member;
import librarymanagement.entities.Book;
import librarymanagement.entities.Reservation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MemberService {

    private MemberRepository memberRepository;
    private TransactionRepository transactionRepository;
    private ReservationRepository reservationRepository;
    private NotificationService notificationService;

    public Member registerMember(Member member) {
        // Generate unique barcode
        member.setId("MEMBER-" + UUID.randomUUID().toString());
        return memberRepository.save(member);
    }

    public boolean canCheckoutBooks(String memberId) {
        long activeLoans = transactionRepository.countByMemberMemberIdAndReturnDateIsNull(memberId);
        return activeLoans < 5; // Max 5 books allowed
    }

    public List<Book> getCheckedOutBooks(String memberId) {
        return transactionRepository.findByMemberMemberIdAndReturnDateIsNull(memberId)
                .stream()
                .map(loan -> loan.getBookItem().getBook())
                .collect(Collectors.toList());
    }

    public List<Reservation> getMemberReservations(String memberId) {
        return reservationRepository.findByMemberMemberId(memberId);
    }
}
