package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Amenity;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.AmenityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class AmenityController {

    @Autowired
    AmenityService amenityService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/amenities/categoryAmenities/{categoryId}")
    public ResponseEntity<ResponseMessage> createAmenity(@PathVariable("categoryId") String categoryId,
                                                             @RequestBody @Valid CustomPayload amenityPayload) {
        Amenity amenity = convertAmenityToAmenityPayload(amenityPayload);
        amenityService.createAmenity(categoryId, amenity);
        message = "Amenity created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("protected/amenities/{amenityId}")
    public ResponseEntity<ResponseMessage> editAmenity(@PathVariable("amenityId") String amenityId,
                                                               @RequestBody @Valid CustomPayload amenityPayload) {
        Amenity amenity = convertAmenityToAmenityPayload(amenityPayload);
        amenityService.editAmenity(amenityId, amenity);
        message = "Amenity edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/amenities")
    public ResponseEntity<List<CustomDto>> getAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        List<CustomDto> amenityDtos = convertCitiesToCityDtos(amenities);
        return new ResponseEntity<>(amenityDtos, HttpStatus.OK);
    }

    @GetMapping("public/amenities/categoryAmenities/{categoryId}")
    public ResponseEntity<List<CustomDto>> getAmenitiesByCategory(@PathVariable("categoryId")String categoryId) {
        List<Amenity> amenities = amenityService.getAmenitiesByCategory(categoryId);
        List<CustomDto> amenityDtos = convertCitiesToCityDtos(amenities);
        return new ResponseEntity<>(amenityDtos, HttpStatus.OK);

    }

    @GetMapping("public/amenities/{amenityId}")
    public ResponseEntity<CustomDto> getAmenity(@PathVariable("amenityId") String amenityId) {
        Amenity amenity = amenityService.getAmenity(amenityId);
        CustomDto amenityDto = this.modelMapper.map(amenity, CustomDto.class);
        return new ResponseEntity<>(amenityDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("protected/amenities/{amenityId}")
    public ResponseEntity<ResponseMessage> deleteAmenity(@PathVariable("amenityId") String amenityId) {
        amenityService.deleteAmenity(amenityId);
        message = "Amenity deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    private Amenity convertAmenityToAmenityPayload(CustomPayload amenityPayload) {
        return this.modelMapper.map(amenityPayload, Amenity.class);
    }

    private List<CustomDto> convertCitiesToCityDtos(List<Amenity> amenities) {
        return amenities.stream()
                .map(amenity -> this.modelMapper.map(amenity, CustomDto.class))
                .collect(Collectors.toList());
    }
}
