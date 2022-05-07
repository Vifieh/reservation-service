package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.PropertyDto;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.payload.PropertyPayload;
import com.reservation.reservationservice.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<PropertyDto> createProperty(@PathVariable("propertyTypeId") String propertyTypeId,
                                                      @RequestBody @Valid PropertyPayload propertyPayload) {
        Property property = this.modelMapper.map(propertyPayload, Property.class);
        Property property1 = propertyService.saveProperty(propertyTypeId, property);
        PropertyDto propertyDto = this.modelMapper.map(property1, PropertyDto.class);
        return new ResponseEntity<>(propertyDto, HttpStatus.CREATED);
    }



}
