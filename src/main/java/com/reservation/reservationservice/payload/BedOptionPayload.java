package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BedOptionPayload {

    private String bedAvailableId;
    private int numberOfBeds;
    private int numberOfGuests;
}