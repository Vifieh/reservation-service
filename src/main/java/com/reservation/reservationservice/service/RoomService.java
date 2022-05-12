package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.payload.RoomPayload;

public interface RoomService {

    void createRoom(String propertyId, String roomNameId, RoomPayload roomPayload);
}
