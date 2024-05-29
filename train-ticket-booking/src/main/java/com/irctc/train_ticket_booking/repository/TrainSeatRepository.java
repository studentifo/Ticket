package com.irctc.train_ticket_booking.repository;

import com.irctc.train_ticket_booking.model.TrainSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainSeatRepository extends JpaRepository<TrainSeat, Long> {
    List<TrainSeat> findBySection(String section);

    TrainSeat findBySectionAndSeatNumber(String section, int seatNumber);
}