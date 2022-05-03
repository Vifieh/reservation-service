package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.CountryService;
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
public class CountryController {

    @Autowired
    CountryService countryService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/countries")
    public ResponseEntity<ResponseMessage> createCountry(@RequestBody CustomPayload countryPayload) {
        Country country = this.modelMapper.map(countryPayload, Country.class);
        countryService.createCountry(country);
        message = "Country created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("protected/countries/{countryId}")
    public ResponseEntity<ResponseMessage> editCountry(@PathVariable("countryId") String countryId,
                                                       @RequestBody CustomPayload countryPayload) {
        Country country = this.modelMapper.map(countryPayload, Country.class);
        countryService.editCountry(countryId, country);
        message = "Country edited successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/countries")
    public ResponseEntity<List<CustomDto>> getCountries() {
        List<Country> countries = countryService.getAllCountries();
        List<CustomDto> countryDTOS = countries.stream()
                .map(country -> this.modelMapper.map(country, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(countryDTOS, HttpStatus.OK);
    }

    @GetMapping("public/countries/{countryId}")
    public ResponseEntity<CustomDto> getCountry(@PathVariable("countryId") String countryId) {
        Country country = countryService.getCountry(countryId);
        CustomDto countryDto = this.modelMapper.map(country, CustomDto.class);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("protected/countries/{countryId}")
    public ResponseEntity<ResponseMessage> deleteCountry(@PathVariable("countryId") String countryId) {
        countryService.deleteCountry(countryId);
        message = "Country deleted successfully";
        return new ResponseEntity<>(new ResponseMessage(message) , HttpStatus.NO_CONTENT);
    }
}
