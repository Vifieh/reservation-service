package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.payload.RoomPayload;
import com.reservation.reservationservice.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/rooms")
    public ResponseEntity<ResponseMessage> createRoomName(@RequestParam("propertyId") String propertyId,
                                                          @RequestParam("roomTypeId") String roomTypeId,
                                                          @RequestParam("roomNameId") String roomNameId,
                                                          @RequestBody @Valid RoomPayload roomPayload) {
        Room room = this.modelMapper.map(roomPayload, Room.class);
        roomService.createRoom(propertyId, roomTypeId, roomNameId, room);
        message = "Room created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

}
