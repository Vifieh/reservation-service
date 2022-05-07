package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BedAvailable;
import lombok.Data;

@Data
public class BedOptionDto {
    private String id;
    private BedAvailable bed;
    private int numberOfBeds;
    private int numberOfGuests;
}
