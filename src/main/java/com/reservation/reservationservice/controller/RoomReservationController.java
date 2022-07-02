package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.RoomReservationDto;
import com.reservation.reservationservice.dto.RoomReservationDto.ReservationContactDetailsDto;
import com.reservation.reservationservice.model.RoomReservation;
import com.reservation.reservationservice.payload.RoomReservationPayload;
import com.reservation.reservationservice.service.RoomReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.reservation.reservationservice.dto.RoomReservationDto.*;


/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@RequestMapping("/api/v1/protected/")
@RestController
@CrossOrigin()
public class RoomReservationController {

    @Autowired
    RoomReservationService reservationService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("roomReservation")
    public ResponseEntity<ResponseMessage> reserveRoom(@RequestParam String propertyId,
                                                       @RequestBody @Valid RoomReservationPayload reservationPayload) {
        reservationService.reserveRoom(propertyId, reservationPayload);
        message = "Room reserved successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("reservations/properties/{propertyId}")
    public ResponseEntity<List<RoomReservationDto>> getReservationsByProperty(@PathVariable String propertyId) {
        List<RoomReservation> roomReservationList = reservationService.getRoomReservationsByProperty(propertyId);
        List<RoomReservationDto> roomReservationDtoList = roomReservationList.stream().map(reservation ->  {
            CustomDto countryDto = modelMapper.map(reservation.getContactDetails().getCountry(), CustomDto.class);
            ReservationContactDetailsDto reservationContactDetailsDto = new ReservationContactDetailsDto(reservation.getContactDetails().getId(),
                    reservation.getContactDetails().getFirstName(), reservation.getContactDetails().getLastName(), reservation.getContactDetails().getEmail(),
                    reservation.getContactDetails().getPhoneNumber(), countryDto);
            List<RoomReservationItemDto> roomReservationItemDto = reservation.getRoomReservationItemList().stream().map(roomReservationItem -> {
                CustomDto roomDto = new CustomDto(roomReservationItem.getRoom().getId(), roomReservationItem.getRoom().getRoomName().getName());
                return new RoomReservationItemDto(roomReservationItem.getId(), roomReservationItem.getNumberOfRooms(), roomReservationItem.getPrice(),
                        roomReservationItem.getFullGuestName(), roomReservationItem.getGuestEmail(), roomReservationItem.getCurrency(), roomDto);
            }).collect(Collectors.toList());
            return new RoomReservationDto(reservation.getId(), reservation.getCheckIn(), reservation.getCheckOut(), reservation.getTotalPrice(),
                    reservation.getNumberOfAdults(), reservation.getNumberOfChildren(), reservation.getSpecialRequest(),
                    reservation.getArrivalTime(), reservation.getCurrency(), reservationContactDetailsDto, roomReservationItemDto);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(roomReservationDtoList, HttpStatus.OK);
    }

}
