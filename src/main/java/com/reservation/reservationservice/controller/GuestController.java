package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Guest;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.GuestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/protected/")
@CrossOrigin
@RestController
public class GuestController {

    @Autowired
    GuestService guestService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("guests")
    public ResponseEntity<ResponseMessage> createGuest(@RequestBody CustomPayload guestPayload) {
        Guest guest = convertGuestToGuestPayload(guestPayload);
        guestService.createGuest(guest);
        message = "Guest created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("guests/{guestId}")
    public ResponseEntity<ResponseMessage> editGuest(@PathVariable("guestId") String guestId,
                                                       @RequestBody CustomPayload guestPayload) {
        Guest guest = convertGuestToGuestPayload(guestPayload);
        guestService.editGuest(guestId, guest);
        message = "Guest edited successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("guests")
    public ResponseEntity<List<CustomDto>> getGuests() {
        List<Guest> guests = guestService.getAllGuests();
        List<CustomDto> guestDTOS = guests.stream()
                .map(guest -> this.modelMapper.map(guest, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(guestDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("guests/{guestId}")
    public ResponseEntity<CustomDto> getGuest(@PathVariable("guestId") String guestId) {
        Guest guest = guestService.getGuest(guestId);
        CustomDto guestDto = this.modelMapper.map(guest, CustomDto.class);
        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("guests/{guestId}")
    public ResponseEntity<ResponseMessage> deleteGuest(@PathVariable("guestId") String guestId) {
        guestService.deleteGuest(guestId);
        message = "Guest deleted successfully";
        return new ResponseEntity<>(new ResponseMessage(message) , HttpStatus.NO_CONTENT);
    }

    private Guest convertGuestToGuestPayload(CustomPayload guestPayload) {
        return this.modelMapper.map(guestPayload, Guest.class);
    }
}
