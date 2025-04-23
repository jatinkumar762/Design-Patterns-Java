package service;

import dtos.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class TicketService {

    public double calculateAmount(Ticket ticket){

        Duration duration = Duration.between(ticket.getIssueTime(), LocalDateTime.now());

        long hours = duration.toHours();

        if (hours <= 1) return 4.0;

        if (hours <= 3) return 4.0 + (hours - 1) * 3.5;

        return 4.0 + 2 * 3.5 + (hours - 3) * 2.5;
    }
}
