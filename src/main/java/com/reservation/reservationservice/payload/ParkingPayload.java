package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingPayload {

    private Available available;
    private Type type;
    private Site site;
    private Reservation reservation;
    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private Currency currency;

}

