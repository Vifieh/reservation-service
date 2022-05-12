package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.RoomBedAvailablePayload;
import com.reservation.reservationservice.payload.RoomPayload;
import com.reservation.reservationservice.repository.RoomBedAvailableRepository;
import com.reservation.reservationservice.repository.RoomRepository;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final Util util = new Util();

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomBedAvailableRepository roomBedAvailableRepository;

    @Autowired
    PropertyService propertyService;

    @Autowired
    RoomNameService roomNameService;

    @Autowired
    BedAvailableService bedAvailableService;

    @Autowired
    UserService userService;


    @Override
    public void createRoom(String propertyId, String roomNameId, RoomPayload roomPayload) {
        List<RoomBedAvailable> roomBedAvailables = new ArrayList<>();
        User user = userService.getAuthUser();
        Property property = propertyService.getProperty(propertyId);
        RoomName roomName = roomNameService.getRoomName(roomNameId);
        Room room = new Room();
        room.setId(util.generateId());
        room.setUser(user);
        room.setCreatedBy(user.getEmail());
        room.setProperty(property);
        room.setRoomName(roomName);
        room.setName(roomPayload.getName());
        room.setCurrency(roomPayload.getCurrency());
        room.setNumberOfGuests(roomPayload.getNumberOfGuests());
        room.setNumberOfRooms(roomPayload.getNumberOfRooms());
        room.setRoomSize(roomPayload.getRoomSize());
        room.setSize(roomPayload.getSize());
        room.setSmokingPolicy(roomPayload.getSmokingPolicy());
        room.setUnitPrice(roomPayload.getUnitPrice());
        Room room1 = roomRepository.save(room);
        addRoomBedAvailable(roomPayload, room1);
    }

    private void addRoomBedAvailable(RoomPayload roomPayload, Room room1) {
        for (RoomBedAvailablePayload roomBedAvailablePayload : roomPayload.getRoomBedAvailablePayloadList()) {
            BedAvailable bedAvailable = bedAvailableService.getBedAvailable(roomBedAvailablePayload.getBedAvailableId());
            RoomBedAvailable roomBedAvailable = new RoomBedAvailable();
            roomBedAvailable.setId(new BedOptionKey(bedAvailable.getId(), room1.getId()));
            roomBedAvailable.setRoom(room1);
            roomBedAvailable.setBedAvailable(bedAvailable);
            roomBedAvailable.setNumberOfBeds(roomBedAvailablePayload.getNumberOfBeds());
            roomBedAvailableRepository.saveAndFlush(roomBedAvailable);
        }
    }

}
