package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.RoomType;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.RoomTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/protected/")
@CrossOrigin
@RestController
public class RoomTypeController {

    @Autowired
    RoomTypeService roomTypeService;

    @Autowired
    ModelMapper modelMapper;

    private String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roomTypes")
    public ResponseEntity<ResponseMessage> createRoomType(@RequestBody @Valid CustomPayload roomTypePayload) {
        RoomType roomType = convertRoomTypePayloadToRoomType(roomTypePayload);
        roomTypeService.createRoomType(roomType);
        message = "Room type created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/roomTypes/{roomTypeId}")
    public ResponseEntity<ResponseMessage> editRoomType(@PathVariable("roomTypeId") String roomTypeId,
                                                        @RequestBody @Valid CustomPayload roomTypePayload) {
        RoomType roomType = convertRoomTypePayloadToRoomType(roomTypePayload);
        roomTypeService.editRoomType(roomTypeId, roomType);
        message = "Room type edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/roomTypes")
    public ResponseEntity<List<CustomDto>> getRoomTypes() {
       List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
       List<CustomDto> roomTypeDtos = roomTypes.stream().map(roomType ->
               this.modelMapper.map(roomType, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(roomTypeDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("/roomTypes/{roomTypeId}")
    public ResponseEntity<CustomDto> getRoomType(@PathVariable("roomTypeId") String roomTypeId) {
        RoomType roomType = roomTypeService.getRoomType(roomTypeId);
        CustomDto roomTypeDto = this.modelMapper.map(roomType, CustomDto.class);
        return new ResponseEntity<>(roomTypeDto, HttpStatus.OK);
    }

    private RoomType convertRoomTypePayloadToRoomType(CustomPayload roomTypePayload) {
        return this.modelMapper.map(roomTypePayload, RoomType.class);
    }
}
