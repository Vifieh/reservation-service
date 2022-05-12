package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BedAvailable;
import lombok.Data;

@Data
public class RoomBedAvailable {
    private String id;
    private BedAvailable bed;
    private int numberOfBeds;
}
