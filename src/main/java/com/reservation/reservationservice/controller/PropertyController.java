package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.PropertyDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.SuccessResponse;
import com.reservation.reservationservice.model.Language;
import com.reservation.reservationservice.model.Parking;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.payload.ParkingPayload;
import com.reservation.reservationservice.payload.PropertyPayload;
import com.reservation.reservationservice.service.PropertyService;
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
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("properties/propertyTypes/{propertyTypeId}")
    public ResponseEntity<SuccessResponse> createProperty(@PathVariable("propertyTypeId") String propertyTypeId,
                                                          @RequestBody @Valid PropertyPayload propertyPayload) {
        Property property = this.modelMapper.map(propertyPayload, Property.class);
        Property property1 = propertyService.saveProperty(propertyTypeId, property);
        message = "Property created successfully!";
        return new ResponseEntity<>(new SuccessResponse(message, property1.getId()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("parking/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> addParkingFacility(@PathVariable("propertyId") String propertyId,
                                                              @RequestBody @Valid ParkingPayload parkingPayload) {
        Parking parking = this.modelMapper.map(parkingPayload, Parking.class);
        propertyService.addParkingFacility(propertyId, parking);
        message = "Parking facility added successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("language/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> addLanguage(@PathVariable("propertyId") String propertyId,
                                                              @RequestBody @Valid List<CustomPayload> languagePayload) {
        List<Language> languages = languagePayload.stream().map(language ->
                modelMapper.map(language, Language.class)).collect(Collectors.toList());
        propertyService.addLanguage(propertyId, languages);
        message = "Language(s) added successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

}
