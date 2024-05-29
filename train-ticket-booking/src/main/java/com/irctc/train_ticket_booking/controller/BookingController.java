package com.irctc.train_ticket_booking.controller;

import com.irctc.train_ticket_booking.model.Ticket;
import com.irctc.train_ticket_booking.model.TrainSeat;
import com.irctc.train_ticket_booking.model.User;
import com.irctc.train_ticket_booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8080/api/bookings/

@RestController
@RequestMapping("/api/bookings") // context-path
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Ticket bookTicket(@RequestBody User user,
                             @RequestParam String from,
                             @RequestParam String to,
                             @RequestParam double pricePaid,
                             @RequestParam String section) {
        return bookingService.bookTicket(user, from, to, pricePaid, section);
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return bookingService.getTicket(id);
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
    }

    @PutMapping("/{id}/seat")
    public TrainSeat changeSeat(@PathVariable Long id, @RequestParam String section, @RequestParam int seatNumber) {
        return bookingService.changeSeat(id, section, seatNumber);
    }

    @GetMapping("/section/{section}")
    public List<User> getUsersBySection(@PathVariable String section) {
        return bookingService.getUsersBySection(section);
    }

    // H2 database
}