package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.payload.RoomAmenityPayload;
import com.reservation.reservationservice.payload.RoomPayload;

import java.util.List;

public interface RoomService {

    void createRoom(String propertyId, String roomNameId, RoomPayload roomPayload);

    void addRoomAmenities(List<RoomAmenityPayload> roomAmenityPayload);

    Room getRoom(String roomId);
}
