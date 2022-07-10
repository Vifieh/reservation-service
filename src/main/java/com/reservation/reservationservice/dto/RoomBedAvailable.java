package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BedAvailable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBedAvailable {
    private String id;
    private BedAvailable bed;
    private int numberOfBeds;
}
