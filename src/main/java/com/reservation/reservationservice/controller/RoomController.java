package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.RoomBedAvailableDto;
import com.reservation.reservationservice.dto.RoomDto;
import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.payload.ExtraBedOptionsAndRoomAmenitiesPayload;
import com.reservation.reservationservice.payload.ExtraBedPayload;
import com.reservation.reservationservice.payload.RoomPayload;
import com.reservation.reservationservice.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("protected/rooms")
    public ResponseEntity<ResponseMessage> createRoom(@RequestParam("propertyId") String propertyId,
                                                      @RequestBody @Valid RoomPayload roomPayload) {
        roomService.createRoom(propertyId, roomPayload);
        message = "Room created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('MANAGER')")
//    @PostMapping("protected/roomAmenities")
//    public ResponseEntity<ResponseMessage> addRoomAmenities(@RequestBody @Valid List<RoomAmenityPayload> roomAmenityPayloadList) {
//        roomService.addRoomAmenities(roomAmenityPayloadList);
//        message = "Room amenities successfully!";
//        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
//    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("rooms/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> addExtraBedOptionsAndRoomAmenities(@PathVariable String propertyId,
                                                                                  @RequestBody @Valid ExtraBedOptionsAndRoomAmenitiesPayload bedOptionsAndRoomAmenitiesPayload) {

        roomService.addExtraBedOptionsAndRoomAmenities(propertyId, bedOptionsAndRoomAmenitiesPayload);
        message = "Amenities added successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @GetMapping("public/rooms")
    public ResponseEntity<List<RoomDto>> getAllRoomsOfUserByProperty(@RequestParam("propertyId") String propertyId) {
        List<Room> rooms = roomService.getAllRoomsOfUserByProperty(propertyId);
        List<RoomDto> roomDtoList = rooms.stream().map(room -> {
            List<RoomBedAvailableDto> roomBedAvailableDtoList = room.getRoomBedAvailables()
                    .stream().map(roomBedAvailable -> modelMapper.map(roomBedAvailable, RoomBedAvailableDto.class)).collect(Collectors.toList());
            return new RoomDto(room.getId(), room.getName(), room.getNumberOfRooms(), room.getRoomSize(),
                    room.getUnitPrice(), room.getNumberOfGuests(), room.getRoomName().getName(), room.getSize(), room.getSmokingPolicy(),
                    room.getCurrency(), roomBedAvailableDtoList);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(roomDtoList, HttpStatus.OK);
    }
}
