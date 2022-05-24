package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.RoomName;
import com.reservation.reservationservice.model.RoomType;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.RoomNameRepository;
import com.reservation.reservationservice.repository.RoomTypeRepository;
import com.reservation.reservationservice.service.RoomNameService;
import com.reservation.reservationservice.service.RoomTypeService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomNameServiceImpl implements RoomNameService {
    private final Util util = new Util();

    @Autowired
    RoomNameRepository roomNameRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoomTypeService roomTypeService;

    @Override
    public void createRoomName(String roomTypeId, RoomName roomName) {
        User user = userService.getAuthUser();
        Optional<RoomName> city1= roomNameRepository.findByName(roomName.getName());
        if (city1.isPresent()) {
            throw new ResourceAlreadyExistException("Room name already exist");
        }
        RoomType roomType = roomTypeService.getRoomType(roomTypeId);
        roomName.setId(util.generateId());
        roomName.setUser(user);
        roomName.setRoomType(roomType);
        roomName.setCreatedBy(user.getEmail());
        roomNameRepository.save(roomName);
    }

    @Override
    public void editRoomName(String roomNameId, RoomName roomName) {
        User user = userService.getAuthUser();
        RoomName roomName1 = getRoomName(roomNameId);
        roomName1.setName(roomName.getName());
        roomName1.setUser(user);
        roomName1.setCreatedBy(user.getEmail());
        roomNameRepository.save(roomName1);
    }

    @Override
    public List<RoomName> getAllRoomNames() {
        return roomNameRepository.findAll();
    }

    @Override
    public List<RoomName> getRoomNamesByRoomType(String roomTypeId) {
        return roomTypeService.getRoomType(roomTypeId).getRoomNames();
    }

    @Override
    public RoomName getRoomName(String roomNameId) {
        Optional<RoomName> roomName = roomNameRepository.findById(roomNameId);
        roomName.orElseThrow(() -> new ResourceNotFoundException("Room name not found with id - " + roomNameId));
        return roomName.get();
    }

    @Override
    public void deleteRoomName(String roomNameId) {
        roomNameRepository.deleteById(roomNameId);
    }
}
