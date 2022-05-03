package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.CityService;
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
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/protected/cities/countries/{countryId}")
    public ResponseEntity<ResponseMessage> createCity(@PathVariable("countryId") String countryId,
                                                      @RequestBody CustomPayload cityPayload) {
        City city = this.modelMapper.map(cityPayload, City.class);
        cityService.createCity(countryId, city);
        message = "City created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("protected/cities/{cityId}")
    public ResponseEntity<ResponseMessage> editCity(@PathVariable("cityId") String cityId,
                                                       @RequestBody CustomPayload cityPayload) {
        City city = this.modelMapper.map(cityPayload, City.class);
        cityService.editCity(cityId, city);
        message = "City edited successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/cities")
    public ResponseEntity<List<CustomDto>> getCities() {
        List<City> cities = cityService.getAllCities();
        List<CustomDto> cityDTOS = cities.stream()
                .map(city -> this.modelMapper.map(city, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(cityDTOS, HttpStatus.OK);
    }

    @GetMapping("public/cities/{cityId}")
    public ResponseEntity<CustomDto> getCity(@PathVariable("cityId") String cityId) {
        City city = cityService.getCity(cityId);
        CustomDto cityDto = this.modelMapper.map(city, CustomDto.class);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("protected/cities/{cityId}")
    public ResponseEntity<ResponseMessage> deleteCity(@PathVariable("cityId") String cityId) {
        cityService.deleteCity(cityId);
        message = "City deleted successfully";
        return new ResponseEntity<>(new ResponseMessage(message) , HttpStatus.NO_CONTENT);
    }
}
