package com.irctc.train_ticket_booking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String section;
    private int seatNumber;

    @OneToOne
    @JoinColumn(name = "ticket_id",referencedColumnName = "id")
    @JsonBackReference
    private Ticket ticket;

    public TrainSeat(String section, int seatNumber) {
        this.section = section;
        this.seatNumber = seatNumber;
    }
    // Getters and Setters
}