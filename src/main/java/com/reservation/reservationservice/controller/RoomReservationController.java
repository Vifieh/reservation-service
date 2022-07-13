package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.*;
import com.reservation.reservationservice.model.RoomReservation;
import com.reservation.reservationservice.payload.RoomReservationPayload;
import com.reservation.reservationservice.service.RoomReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.reservation.reservationservice.dto.RoomReservationDto.*;
import static org.springframework.http.HttpStatus.*;


/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@RequestMapping("/api/v1/")
@RestController
@CrossOrigin()
public class RoomReservationController {

    @Autowired
    RoomReservationService reservationService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("protected/roomReservations")
    public ResponseEntity<RoomReservationResponse> reserveRoom(@RequestParam String propertyId,
                                                       @RequestBody @Valid RoomReservationPayload reservationPayload) {
       RoomReservation roomReservation = reservationService.reserveRoom(propertyId, reservationPayload);
       message = "Room(s) served successfully";
        RoomReservationResponse reservationResponse =  new RoomReservationResponse(roomReservation.getId(), roomReservation.getCheckIn(), roomReservation.getCheckOut(), roomReservation.getTotalPrice(),
                roomReservation.getNumberOfAdults(), roomReservation.getNumberOfChildren(), roomReservation.getSpecialRequest(),
                roomReservation.getArrivalTime(), roomReservation.getRef(), roomReservation.getCurrency());
        return new ResponseEntity<>( reservationResponse, CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("protected/reservations/properties/{propertyId}")
    public ResponseEntity<List<RoomReservationDto>> getReservationsByProperty(@PathVariable String propertyId,
                                                                              @RequestParam boolean hasCheckedOut) {
        List<RoomReservation> roomReservationList = reservationService.getRoomReservationsByProperty(propertyId, hasCheckedOut);
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
                    reservation.getArrivalTime(), reservation.getRef(), reservation.getCurrency(), reservationContactDetailsDto, roomReservationItemDto);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(roomReservationDtoList, OK);
    }
    @GetMapping("public/reservations/{reservationsId}")
    public ResponseEntity<RoomReservationDTO> getReservation(@PathVariable String reservationsId) {
        RoomReservation roomReservation = reservationService.getRoomReservation(reservationsId);
        RoomReservationDTO roomReservationDTO = this.modelMapper.map(roomReservation, RoomReservationDTO.class);
        return new ResponseEntity<>(roomReservationDTO, OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PatchMapping("roomReservations")
    public ResponseEntity<ResponseMessage> checkOutGuest(@RequestParam String reservationId) {
       reservationService.checkOutGuest(reservationId);
       message = "Guest has been checked out successfully";
        return new ResponseEntity<>(new ResponseMessage(message), OK);
    }

}
