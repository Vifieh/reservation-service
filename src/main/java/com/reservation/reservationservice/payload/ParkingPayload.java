package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Available;
import com.reservation.reservationservice.constants.Reservation;
import com.reservation.reservationservice.constants.Site;
import com.reservation.reservationservice.constants.Type;
import lombok.Data;

@Data
public class ParkingPayload {

    private Available available;
    private Type type;
    private Site site;
    private Reservation reservation;
    private double unitPrice;

}

