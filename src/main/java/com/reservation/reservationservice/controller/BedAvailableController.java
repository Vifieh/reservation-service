package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.BedAvailable;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.BedAvailableService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class BedAvailableController {

    @Autowired
    BedAvailableService bedAvailableService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/bedsAvailable")
    public ResponseEntity<ResponseMessage> createBedAvailable(@RequestBody CustomPayload bedAvailablePayload) {
        BedAvailable bedAvailable = modelMapper.map(bedAvailablePayload, BedAvailable.class);
        bedAvailableService.createBedAvailable(bedAvailable);
        message = "Bed Available created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("protected/bedsAvailable/{bedId}")
    public ResponseEntity<ResponseMessage> editBedAvailable(@PathVariable String bedId,
                                                            @RequestBody CustomPayload bedAvailablePayload) {
        BedAvailable bedAvailable = modelMapper.map(bedAvailablePayload, BedAvailable.class);
        bedAvailableService.editBedAvailable(bedId, bedAvailable);
        message = "Bed Available edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/bedsAvailable")
    public ResponseEntity<List<CustomDto>> getBedsAvailable() {
        List<BedAvailable> bedsAvailable = bedAvailableService.getAllBedsAvailable();
        List<CustomDto> bedsAvailableDtos = bedsAvailable.stream().map(bedAvailable ->
                modelMapper.map(bedAvailable, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(bedsAvailableDtos, HttpStatus.OK);
    }

    @GetMapping("public/bedsAvailable/{bedId}")
    public ResponseEntity<CustomDto> getBedAvailable(@PathVariable String bedId) {
        BedAvailable bedAvailable = bedAvailableService.getBedAvailable(bedId);
        CustomDto bedsAvailableDto = modelMapper.map(bedAvailable, CustomDto.class);
        return new ResponseEntity<>(bedsAvailableDto, HttpStatus.OK);
    }

}
