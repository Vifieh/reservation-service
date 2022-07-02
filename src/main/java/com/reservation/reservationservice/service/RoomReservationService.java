package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.RoomReservation;
import com.reservation.reservationservice.payload.RoomReservationPayload;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

public interface RoomReservationService {

    void reserveRoom(String propertyId, RoomReservationPayload reservationPayload);

    List<RoomReservation> getRoomReservationsByProperty(String propertyId);
}
