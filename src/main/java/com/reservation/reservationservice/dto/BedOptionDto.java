package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.Bed;
import lombok.Data;

@Data
public class BedOptionDto {
    private String id;
    private Bed bed;
    private int numberOfBeds;
    private int numberOfGuests;
}
