package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.RoomBedAvailable;
import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.payload.RoomAmenityPayload;
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
                                                      @RequestParam("roomNameId") String roomNameId,
                                                      @RequestBody @Valid RoomPayload roomPayload) {
        roomService.createRoom(propertyId, roomNameId, roomPayload);
        message = "Room created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("protected/roomAmenities")
    public ResponseEntity<ResponseMessage> addRoomAmenities(@RequestBody @Valid List<RoomAmenityPayload> roomAmenityPayloadList) {
        roomService.addRoomAmenities(roomAmenityPayloadList);
        message = "Room amenities successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

}
