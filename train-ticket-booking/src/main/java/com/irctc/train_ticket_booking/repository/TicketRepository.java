package com.irctc.train_ticket_booking.repository;

import com.irctc.train_ticket_booking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}