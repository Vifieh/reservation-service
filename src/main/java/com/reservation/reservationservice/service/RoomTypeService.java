package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.RoomType;

import java.util.List;

public interface RoomTypeService {

    void createRoomType(RoomType roomType);

    void editRoomType(String roomTypeId, RoomType roomType);

    List<RoomType> getAllRoomTypes();

    RoomType getRoomType(String roomTypeId);

    void deleteRoomType(String roomTypeId);
}
