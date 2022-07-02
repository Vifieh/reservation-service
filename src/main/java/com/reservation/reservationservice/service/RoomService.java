package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.payload.ExtraBedOptionsAndRoomAmenitiesPayload;
import com.reservation.reservationservice.payload.RoomPayload;

import java.util.List;

public interface RoomService {

    void createRoom(String propertyId, RoomPayload roomPayload);

    void addExtraBedOptionsAndRoomAmenities(String propertyId, ExtraBedOptionsAndRoomAmenitiesPayload bedOptionsRoomAmenitiesPayload);

    Room getRoom(String roomId);

    List<Room> getAllRoomsOfUserByProperty(String propertyId);

    List<Room> getAllRoomsByProperty(String propertyId);
}
