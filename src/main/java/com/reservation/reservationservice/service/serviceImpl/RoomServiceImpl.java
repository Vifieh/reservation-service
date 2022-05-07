package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.repository.RoomRepository;
import com.reservation.reservationservice.service.RoomService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private Util util = new Util();

    @Autowired
    RoomRepository roomRepository;

    @Override
    public void CreateRoom(String propertyId, Room room) {

    }
}
