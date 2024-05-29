package com.irctc.train_ticket_booking.service;

import com.irctc.train_ticket_booking.model.Ticket;
import com.irctc.train_ticket_booking.model.TrainSeat;
import com.irctc.train_ticket_booking.model.User;
import com.irctc.train_ticket_booking.repository.TicketRepository;
import com.irctc.train_ticket_booking.repository.TrainSeatRepository;
import com.irctc.train_ticket_booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainSeatRepository trainSeatRepository;

    @Transactional
    public Ticket bookTicket(User user, String from, String to, double pricePaid, String section) {
        user = userRepository.save(user);
        TrainSeat seat = findAvailableSeat(section);
        if (seat == null) {
            throw new RuntimeException("No available seats in section " + section);
        }
        Ticket ticket = new Ticket();
        ticket.setFromLocation(from);
        ticket.setToLocation(to);
        ticket.setPricePaid(pricePaid);
        ticket.setUser(user);
        ticket.setTrainSeat(seat);
        seat.setTicket(ticket);
        return ticketRepository.save(ticket);
    }

    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public void cancelBooking(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        TrainSeat seat = ticket.getTrainSeat();
        seat.setTicket(null);
        ticketRepository.delete(ticket);
    }

    @Transactional
    public TrainSeat changeSeat(Long ticketId, String section, int seatNumber) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        TrainSeat oldSeat = ticket.getTrainSeat();
        TrainSeat newSeat = trainSeatRepository.findBySectionAndSeatNumber(section, seatNumber);
        if (newSeat == null) {
            throw new RuntimeException("Seat not available");
        }
        oldSeat.setTicket(null);
        ticket.setTrainSeat(newSeat);
        newSeat.setTicket(ticket);
        ticketRepository.save(ticket);
        return newSeat;
    }

    public List<User> getUsersBySection(String section) {
        return userRepository.findAll().stream()
                .filter(user -> user.getTicket()
                        .stream()
                        .anyMatch(ticket -> ticket.getTrainSeat().getSection().equals(section)))
                .collect(Collectors.toList());
    }

    private TrainSeat findAvailableSeat(String section) {
        return trainSeatRepository.findBySection(section).stream()
                .filter(seat -> seat.getTicket() == null)
                .findFirst()
                .orElse(null);
    }
}