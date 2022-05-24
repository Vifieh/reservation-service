package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.RoomName;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.RoomNameService;
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
public class RoomNameController {

    @Autowired
    RoomNameService roomNameService;

    @Autowired
    ModelMapper modelMapper;

    private String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("roomNames/roomTypes/{roomTypeId}")
    public ResponseEntity<ResponseMessage> createRoomName(@PathVariable("roomTypeId") String roomTypeId,
                                                          @RequestBody @Valid CustomPayload roomNamePayload) {
        RoomName roomName = convertRoomNamePayloadToRoomName(roomNamePayload);
        roomNameService.createRoomName(roomTypeId, roomName);
        message = "Room Name created successfully!";
       return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("roomNames/{roomNameId}")
    public ResponseEntity<ResponseMessage> editRoomName(@PathVariable("roomNameId") String roomNameId,
                                                          @RequestBody @Valid CustomPayload roomNamePayload) {
        RoomName roomName = convertRoomNamePayloadToRoomName(roomNamePayload);
        roomNameService.editRoomName(roomNameId, roomName);
        message = "Room Name edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("roomNames")
    public ResponseEntity<List<CustomDto>> getRoomNames() {
        List<RoomName> roomNames = roomNameService.getAllRoomNames();
        List<CustomDto> roomNameDtos = convertRoomNamesToRoomNameDtos(roomNames);
        return new ResponseEntity<>(roomNameDtos, HttpStatus.OK);
    }

    @GetMapping("roomNames/roomTypes/{roomTypeId}")
    public ResponseEntity<List<CustomDto>> getRoomNamesByRoomType(@PathVariable("roomTypeId") String roomTypeId) {
        List<RoomName> roomNames = roomNameService.getRoomNamesByRoomType(roomTypeId);
        List<CustomDto> roomNameDtos = convertRoomNamesToRoomNameDtos(roomNames);
        return new ResponseEntity<>(roomNameDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("roomNames/{roomNameId}")
    public ResponseEntity<CustomDto> getRoomName(@PathVariable("roomNameId") String roomNameId) {
        RoomName roomName = roomNameService.getRoomName(roomNameId);
        CustomDto roomNameDto = this.modelMapper.map(roomName, CustomDto.class);
        return new ResponseEntity<>(roomNameDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("roomNames/{roomNameId}")
    public ResponseEntity<ResponseMessage> deleteRoomName(@PathVariable String roomNameId) {
        roomNameService.deleteRoomName(roomNameId);
        message = "Room name deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    private RoomName convertRoomNamePayloadToRoomName(CustomPayload roomNamePayload) {
        return this.modelMapper.map(roomNamePayload, RoomName.class);
    }

    private List<CustomDto> convertRoomNamesToRoomNameDtos(List<RoomName> roomNames) {
        return roomNames.stream().map(roomName ->
                this.modelMapper.map(roomName, CustomDto.class)).collect(Collectors.toList());
    }
}
