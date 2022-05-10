package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.repository.ParkingRepository;
import com.reservation.reservationservice.repository.RoomRepository;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {
    private Util util = new Util();

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    ParkingRepository bedOptionRepository;

    @Autowired
    PropertyService propertyService;

    @Autowired
    RoomTypeService roomTypeService;

    @Autowired
    RoomNameService roomNameService;

    @Autowired
    BedAvailableService bedAvailableService;

    @Autowired
    UserService userService;


    @Override
    public void createRoom(String propertyId, String roomTypeId, String roomNameId, Room room) {
        User user = userService.getAuthUser();
        Property property = propertyService.getProperty(propertyId);
        RoomType roomType = roomTypeService.getRoomType(roomTypeId);
        RoomName roomName = roomNameService.getRoomName(roomNameId);
        room.setId(util.generateId());
        room.setUser(user);
        room.setCreatedBy(user.getEmail());
        room.setProperty(property);
        room.setRoomType(roomType);
        room.setRoomName(roomName);
        System.out.println(room);
        roomRepository.save(room);
    }

//   private List<BedOption> addBedOption(Room room, BedAvailable bedAvailable, List<BedOption> bedOptions) {
//        bedOptions.stream().map(bedOption -> {
//            BedOption bedOption1 = new BedOption();
//            bedOption1.setId(new BedOptionKey(bedAvailable.getId(), room.getId()));
//            bedOption1.setRoom(room);
//            bedOption1.setBedAvailable(bedAvailable);
//        } )
//   }

}
