package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.FacilityDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Facility;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.payload.FacilityPayload;
import com.reservation.reservationservice.service.FacilityService;
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
public class FacilityController {

    @Autowired
    FacilityService facilityService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("facilities")
    public ResponseEntity<ResponseMessage> createFacility(@RequestBody FacilityPayload facilityPayload) {
        Facility facility = modelMapper.map(facilityPayload, Facility.class);
        facilityService.createFacility(facility);
        message = "Facility created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("facilities/{facilityId}")
    public ResponseEntity<ResponseMessage> editFacility(@PathVariable String facilityId,
                                                            @RequestBody FacilityPayload facilityPayload) {
        Facility facility = modelMapper.map(facilityPayload, Facility.class);
        facilityService.editFacility(facilityId, facility);
        message = "Facility edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("facilities")
    public ResponseEntity<List<FacilityDto>> getFacilities() {
        List<Facility> facilities = facilityService.getAllFacilities();
        List<FacilityDto> facilitiesDtos = facilities.stream().map(facility ->
                modelMapper.map(facility, FacilityDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(facilitiesDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("facilities/{facilityId}")
    public ResponseEntity<FacilityDto> getFacility(@PathVariable String facilityId) {
        Facility facility = facilityService.getFacility(facilityId);
        FacilityDto facilitiesDto = modelMapper.map(facility, FacilityDto.class);
        return new ResponseEntity<>(facilitiesDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("facilities/{facilityId}")
    public ResponseEntity<ResponseMessage> deleteFacility(@PathVariable String facilityId) {
        facilityService.deleteFacility(facilityId);
        message = "Facility deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }
}
