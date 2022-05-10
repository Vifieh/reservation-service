package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.BedOption;
import com.reservation.reservationservice.model.Room;

public interface RoomService {

    void createRoom(String propertyId, String roomTypeId, String roomNameId, Room room);
}
