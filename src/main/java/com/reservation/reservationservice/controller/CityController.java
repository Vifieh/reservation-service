package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CityDto;
import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.PropertyAddressDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.FileInfo;
import com.reservation.reservationservice.model.PropertyAddress;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.CityService;
import com.reservation.reservationservice.service.FileStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/protected/cities/countries/{countryId}")
    public ResponseEntity<ResponseMessage> createCity(@PathVariable("countryId") String countryId,
                                                      @RequestBody CustomPayload cityPayload) {
        City city = convertCityToCityPayload(cityPayload);
        cityService.createCity(countryId, city);
        message = "City created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("protected/cities/{cityId}")
    public ResponseEntity<ResponseMessage> editCity(@PathVariable("cityId") String cityId,
                                                    @RequestBody CustomPayload cityPayload) {
        City city = convertCityToCityPayload(cityPayload);
        cityService.editCity(cityId, city);
        message = "City edited successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/cities")
    public ResponseEntity<List<CityDto>> getCities() {
        List<City> cities = cityService.getAllCities();
        List<CityDto> cityDtoList = convertCitiesToCitiesDto(cities);
        return new ResponseEntity<>(cityDtoList, HttpStatus.OK);
    }

    @GetMapping("public/cities/countries/{countryId}")
    public ResponseEntity<List<CityDto>> getCitiesByCountry(@PathVariable("countryId") String countryId) {
        List<City> cities = cityService.getCitiesByCountry(countryId);
        List<CityDto> cityDtoList = convertCitiesToCitiesDto(cities);
        return new ResponseEntity<>(cityDtoList, HttpStatus.OK);
    }

    @GetMapping("public/propertyAddress/cities/{cityId}")
    public ResponseEntity<List<PropertyAddressDto>> getPropertyAddressByCity(@PathVariable("cityId") String cityId) {
        List<PropertyAddress> propertyAddressList = cityService.getPropertyAddressByCity(cityId);
        List<PropertyAddressDto> propertyAddressDtoList = getPropertyAddressDtoList(propertyAddressList);
        return new ResponseEntity<>(propertyAddressDtoList, HttpStatus.OK);
    }

    @GetMapping("public/cities/{cityId}")
    public ResponseEntity<CityDto> getCity(@PathVariable("cityId") String cityId) {
        City city = cityService.getCity(cityId);
        List<PropertyAddressDto> propertyAddressDtoList = getPropertyAddressDtoList(city.getPropertyAddresses());
        List<FileInfo> fileInfos = getFileInfoList(city);
        CityDto cityDto = new CityDto(city.getId(), city.getName(), propertyAddressDtoList, fileInfos);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("protected/cities/{cityId}")
    public ResponseEntity<ResponseMessage> deleteCity(@PathVariable("cityId") String cityId) {
        cityService.deleteCity(cityId);
        message = "City deleted successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.NO_CONTENT);
    }

    private City convertCityToCityPayload(CustomPayload cityPayload) {
        return this.modelMapper.map(cityPayload, City.class);
    }

    private List<CityDto> convertCitiesToCitiesDto(List<City> cities) {
        return cities.stream().map(city -> {
                    List<PropertyAddressDto> propertyAddressDtoList = getPropertyAddressDtoList(city.getPropertyAddresses());
                    List<FileInfo> fileInfos = getFileInfoList(city);
                    return new CityDto(city.getId(), city.getName(), propertyAddressDtoList, fileInfos);
                }).collect(Collectors.toList());
    }

    private List<PropertyAddressDto> getPropertyAddressDtoList(List<PropertyAddress> propertyAddressList) {
        return propertyAddressList.stream().map(propertyAddress -> {
            CustomDto cityDto = modelMapper.map(propertyAddress.getCity(), CustomDto.class);
            return new PropertyAddressDto(propertyAddress.getId(), propertyAddress.getStreetAddress(),
                    propertyAddress.getAddressLine2(), propertyAddress.getCode(), cityDto);
        }).collect(Collectors.toList());
    }

    private List<FileInfo> getFileInfoList(City city) {
        return fileStorageService.loadAll(city.getName()).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", city.getName(), path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
    }
}
