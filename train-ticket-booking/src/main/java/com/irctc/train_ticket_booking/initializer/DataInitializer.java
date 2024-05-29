package com.irctc.train_ticket_booking.initializer;

import com.irctc.train_ticket_booking.model.TrainSeat;
import com.irctc.train_ticket_booking.repository.TrainSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TrainSeatRepository trainSeatRepository;

    @Override
    public void run(String... args) throws Exception {
        // Populate Train Seats
        trainSeatRepository.save(new TrainSeat("A", 1));
        trainSeatRepository.save(new TrainSeat("A", 2));
        trainSeatRepository.save(new TrainSeat("B", 1));
        trainSeatRepository.save(new TrainSeat("B", 2));
    }
}
