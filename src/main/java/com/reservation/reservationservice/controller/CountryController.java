package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDTO;
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
    @PostMapping("/protected/countries")
    public ResponseEntity<ResponseMessage> createCountry(@RequestBody CustomPayload countryPayload) {
        Country country = this.modelMapper.map(countryPayload, Country.class);
        countryService.createCountry(country);
        message = "Country created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("protected/countries/{countryId}")
    public ResponseEntity<ResponseMessage> editCountry(@PathVariable String countryId,
                                                       @RequestBody CustomPayload countryPayload) {
        Country country = this.modelMapper.map(countryPayload, Country.class);
        countryService.editCountry(countryId, country);
        message = "Country edited successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/countries")
    public ResponseEntity<List<CustomDTO>> getCountries() {
        List<Country> countries = countryService.getAllCountries();
        List<CustomDTO> countryDTOS = countries.stream()
                .map(country -> this.modelMapper.map(country, CustomDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(countryDTOS, HttpStatus.OK);
    }

    @GetMapping("public/countries/{countryId}")
    public ResponseEntity<CustomDTO> getCountry(@PathVariable String countryId) {
        Country country = countryService.getCountry(countryId);
        CustomDTO countryDto = this.modelMapper.map(country, CustomDTO.class);
        return new ResponseEntity<>(countryDto, HttpStatus.OK);
    }
}
