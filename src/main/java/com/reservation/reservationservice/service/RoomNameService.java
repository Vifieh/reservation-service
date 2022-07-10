package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.RoomName;

import java.util.List;

public interface RoomNameService {

    void createRoomName(String roomTypeId, RoomName roomName);

    void editRoomName(String roomNameId, RoomName roomName);

    List<RoomName> getAllRoomNames();

    List<RoomName> getRoomNamesByRoomType(String roomTypeId);

    RoomName getRoomName(String roomNameId);

    void deleteRoomName(String roomNameId);
}
