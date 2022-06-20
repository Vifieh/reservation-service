package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.AmenityDto;
import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Amenity;
import com.reservation.reservationservice.payload.AmenityPayload;
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

@RequestMapping("api/v1/protected/")
@CrossOrigin
@RestController
public class AmenityController {

    @Autowired
    AmenityService amenityService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("amenities/categoryAmenities/{categoryId}")
    public ResponseEntity<ResponseMessage> createAmenity(@PathVariable("categoryId") String categoryId,
                                                             @RequestBody @Valid AmenityPayload amenityPayload) {
        Amenity amenity = convertAmenityToAmenityPayload(amenityPayload);
        amenityService.createAmenity(categoryId, amenity);
        message = "Amenity created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("amenities/{amenityId}")
    public ResponseEntity<ResponseMessage> editAmenity(@PathVariable("amenityId") String amenityId,
                                                               @RequestBody @Valid AmenityPayload amenityPayload) {
        Amenity amenity = convertAmenityToAmenityPayload(amenityPayload);
        amenityService.editAmenity(amenityId, amenity);
        message = "Amenity edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("amenities")
    public ResponseEntity<List<AmenityDto>> getAmenities(@RequestParam boolean mostRequested) {
        List<Amenity> amenities = amenityService.getAllAmenities(mostRequested);
        List<AmenityDto> amenityDtos = convertCitiesToCityDtos(amenities);
        return new ResponseEntity<>(amenityDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("amenities/categoryAmenities")
    public ResponseEntity<List<AmenityDto>> getAmenitiesByCategory(@RequestParam String categoryId,
                                                                   @RequestParam boolean mostRequested) {
        List<Amenity> amenities = amenityService.getAmenitiesByCategory(categoryId, mostRequested);
        List<AmenityDto> amenityDtos = convertCitiesToCityDtos(amenities);
        return new ResponseEntity<>(amenityDtos, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("amenities/{amenityId}")
    public ResponseEntity<AmenityDto> getAmenity(@PathVariable("amenityId") String amenityId) {
        Amenity amenity = amenityService.getAmenity(amenityId);
        AmenityDto amenityDto = this.modelMapper.map(amenity, AmenityDto.class);
        return new ResponseEntity<>(amenityDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("amenities/{amenityId}")
    public ResponseEntity<ResponseMessage> deleteAmenity(@PathVariable("amenityId") String amenityId) {
        amenityService.deleteAmenity(amenityId);
        message = "Amenity deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    private Amenity convertAmenityToAmenityPayload(AmenityPayload amenityPayload) {
        return this.modelMapper.map(amenityPayload, Amenity.class);
    }

    private List<AmenityDto> convertCitiesToCityDtos(List<Amenity> amenities) {
        return amenities.stream()
                .map(amenity -> this.modelMapper.map(amenity, AmenityDto.class))
                .collect(Collectors.toList());
    }
}
