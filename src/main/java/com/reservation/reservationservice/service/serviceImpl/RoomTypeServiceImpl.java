package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.RoomType;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.RoomTypeRepository;
import com.reservation.reservationservice.service.RoomTypeService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {
    private final Util util = new Util();

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    UserService userService;

    @Override
    public void createRoomType(RoomType roomType) {
        User user = userService.getAuthUser();
        Optional<RoomType> roomType1 = roomTypeRepository.findByName(roomType.getName());
        if (roomType1.isPresent()) {
            throw new ResourceAlreadyExistException("Room Type already exist");
        }
        roomType.setId(util.generateId());
        roomType.setUser(user);
        roomType.setCreatedBy(user.getEmail());
        roomTypeRepository.save(roomType);
    }

    @Override
    public void editRoomType(String roomTypeId, RoomType roomType) {
        User user = userService.getAuthUser();
        RoomType roomType1 = getRoomType(roomTypeId);
        roomType1.setName(roomType.getName());
        roomType1.setUser(user);
        roomType1.setCreatedBy(user.getEmail());
        roomTypeRepository.save(roomType1);
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType getRoomType(String roomTypeId) {
        Optional<RoomType> roomType = roomTypeRepository.findById(roomTypeId);
        roomType.orElseThrow(() -> new ResourceNotFoundException("Room type not found with id - " + roomTypeId));
        return roomType.get();
    }

    @Override
    public void deleteRoomType(String roomTypeId) {
        roomTypeRepository.deleteById(roomTypeId);
    }
}
