package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BedOption {

    @Id
    private String id;
    private int numberOfBeds;
    private int numberOfGuests;
}