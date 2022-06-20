package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.repository.*;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final Util util = new Util();

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomBedAvailableRepository roomBedAvailableRepository;

    @Autowired
    ExtraBedRepository extraBedRepository;

    @Autowired
    GuestExtraBedRepository guestExtraBedRepository;

    @Autowired
    PropertyService propertyService;

    @Autowired
    RoomNameService roomNameService;

    @Autowired
    BedAvailableService bedAvailableService;

    @Autowired
    AmenityService amenityService;

    @Autowired
    UserService userService;

    @Autowired
    GuestService guestService;


    @Override
    public void createRoom(String propertyId, RoomPayload roomPayload) {
        User user = userService.getAuthUser();
        Property property = propertyService.getProperty(propertyId);
        RoomName roomName = roomNameService.getRoomName(roomPayload.getRoomNameId());
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

    @Override
    public void addExtraBedOptionsAndRoomAmenities(String propertyId, ExtraBedOptionsAndRoomAmenitiesPayload extraBedOptionsAndRoomAmenitiesPayload) {
        addExtraBedOption(propertyId, extraBedOptionsAndRoomAmenitiesPayload);
        addRoomAmenities(extraBedOptionsAndRoomAmenitiesPayload);
    }

    @Override
    public Room getRoom(String roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        room.orElseThrow(() -> new ResourceNotFoundException("Room not found with id - " + roomId));
        return room.get();
    }

    @Override
    public List<Room> getAllRoomsOfUserByProperty(String propertyId) {
        User user = userService.getAuthUser();
        Property property = propertyService.getProperty(propertyId);
        return roomRepository.findByUserAndProperty(user, property);
    }


    private void addRoomBedAvailable(RoomPayload roomPayload, Room room1) {
        for (RoomBedAvailablePayload roomBedAvailablePayload : roomPayload.getRoomBedAvailablePayloadList()) {
            BedAvailable bedAvailable = bedAvailableService.getBedAvailable(roomBedAvailablePayload.getBedAvailableId());
            RoomBedAvailable roomBedAvailable = new RoomBedAvailable();
            roomBedAvailable.setId(new RoomBedAvailableKey(bedAvailable.getId(), room1.getId()));
            roomBedAvailable.setRoom(room1);
            roomBedAvailable.setBedAvailable(bedAvailable);
            roomBedAvailable.setNumberOfBeds(roomBedAvailablePayload.getNumberOfBeds());
            roomBedAvailableRepository.saveAndFlush(roomBedAvailable);
        }
    }

    private void addExtraBedOption(String propertyId, ExtraBedOptionsAndRoomAmenitiesPayload extraBedPayload) {
        User user = userService.getAuthUser();
        Property property = propertyService.getProperty(propertyId);
        ExtraBed extraBed = new ExtraBed();
        extraBed.setId(util.generateId());
        extraBed.setNumberOfExtraBeds(extraBedPayload.getExtraBedPayload().getNumberOfExtraBeds());
        extraBed.setUser(user);
        extraBed.setProperty(property);
        ExtraBed extraBed1 = extraBedRepository.save(extraBed);
        extraBedPayload.getExtraBedPayload().getGuestExtraBedPayloadList().forEach(guestExtraBedPayload -> {
            GuestExtraBed guestExtraBed = new GuestExtraBed();
            Guest guest = guestService.getGuest(guestExtraBedPayload.getGuestId());
            guestExtraBed.setId(new GuestExtraBedKey(guest.getId(), extraBed1.getId()));
            guestExtraBed.setExtraBed(extraBed1);
            guestExtraBed.setGuest(guest);
            guestExtraBed.setUnitPrice(guestExtraBedPayload.getUnitPrice());
            guestExtraBed.setRange(guestExtraBedPayload.getRange());
            guestExtraBed.setCurrency(guestExtraBedPayload.getCurrency());
            guestExtraBedRepository.save(guestExtraBed);
        });
    }

    private void addRoomAmenities(ExtraBedOptionsAndRoomAmenitiesPayload bedOptionsRoomAmenitiesPayload) {
        bedOptionsRoomAmenitiesPayload.getRoomAmenityPayloadList().forEach(roomAmenityPayload1 -> {
            Amenity amenity = amenityService.getAmenity(roomAmenityPayload1.getAmenityId());
            roomAmenityPayload1.getRooms().forEach(room -> {
                Room room1 = getRoom(room.getId());
                room1.getAmenities().add(amenity);
                roomRepository.save(room1);
            });
        });
    }
}
