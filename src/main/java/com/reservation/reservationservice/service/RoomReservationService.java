package com.reservation.reservationservice.service;

import com.reservation.reservationservice.dto.RoomReservationDTO;
import com.reservation.reservationservice.model.RoomReservation;
import com.reservation.reservationservice.payload.RoomReservationPayload;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

public interface RoomReservationService {

    RoomReservation reserveRoom(String propertyId, RoomReservationPayload reservationPayload);

    List<RoomReservation> getRoomReservationsByProperty(String propertyId, boolean hasCheckedOut);

    RoomReservation getRoomReservation(String roomReservationId);

    void checkOutGuest(String roomReservationId);
}
