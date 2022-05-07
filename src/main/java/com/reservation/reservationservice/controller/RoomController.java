package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.SuccessResponse;
import com.reservation.reservationservice.model.RoomName;
import com.reservation.reservationservice.payload.CustomPayload;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/rooms")
    public ResponseEntity<SuccessResponse> createRoomName(@RequestParam("roomTypeId") String roomTypeId,
                                                          @RequestParam("roomNameId") String roomNameId,
                                                          @RequestParam("bedAvailableId") String bedAvailableId,
                                                          @RequestBody @Valid) {

        message = "Room Name created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

}
