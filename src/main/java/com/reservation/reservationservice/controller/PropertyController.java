package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.*;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.service.FileStorageService;
import com.reservation.reservationservice.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("protected/properties/propertyTypes/{propertyTypeId}")
    public ResponseEntity<SuccessResponse> createProperty(@PathVariable("propertyTypeId") String propertyTypeId,
                                                          @RequestBody @Valid PropertyPayload propertyPayload) {
        Property property1 = propertyService.saveProperty(propertyTypeId, propertyPayload);
        message = "Property created successfully!";
        return new ResponseEntity<>(new SuccessResponse(message, property1.getId()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("protected/facilitiesServices/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> addFacilityServices(@PathVariable("propertyId") String propertyId,
                                                              @RequestBody @Valid FacilitiesServicesPayload facilitiesServicesPayload) {
        propertyService.addFacilityServices(propertyId, facilitiesServicesPayload);
        message = "Facilities and services added successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("protected/policies/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> addPolicy(@PathVariable("propertyId") String propertyId,
                                                        @RequestBody @Valid PolicyPayload policyPayload) {

        propertyService.addPolicy(propertyId, policyPayload);
        message = "Policy added successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "protected/paymentOptions/properties/{propertyId}", method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseMessage> addPaymentOptions(@PathVariable("propertyId") String propertyId,
                                                        @RequestBody @Valid PaymentOptionWrapper paymentOptionsPayload) {

        propertyService.addPaymentOptions(propertyId, paymentOptionsPayload.getPaymentOptionsPayload());
        message = "PaymentOption(s) added successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @GetMapping("public/properties/{propertyId}")
    public ResponseEntity<PropertyDto> getProperty(@PathVariable String propertyId) {
        Property property = propertyService.getProperty(propertyId);
        List<FileInfo> fileInfos = getFileInfoList(property);
        PropertyContactDetailsDto contactDetailsDto = this.modelMapper.map(property.getPropertyContactDetails(), PropertyContactDetailsDto.class);
        PropertyAddressDto addressDto = this.modelMapper.map(property.getPropertyAddress(), PropertyAddressDto.class);
        PropertyDto propertyDto = new PropertyDto(property.getId(), property.getName(), property.getRating(),
                property.isPending(), property.isCompletedRegistration(), contactDetailsDto, addressDto, fileInfos);
        return new ResponseEntity<>(propertyDto, HttpStatus.OK);
    }

    @GetMapping("public/parking/properties/{propertyId}")
    public ResponseEntity<ParkingDto> getParkingDetailsByProperty(@PathVariable String propertyId) {
        Parking parking = propertyService.getParkingByProperty(propertyId);
        ParkingDto parkingDto = modelMapper.map(parking, ParkingDto.class);
              return new ResponseEntity<>(parkingDto, HttpStatus.OK);
    }

    @GetMapping("public/policy/properties/{propertyId}")
    public ResponseEntity<PolicyDto> getPolicyOfAProperty(@PathVariable String propertyId) {
        Policy policy = propertyService.getPolicyByProperty(propertyId);
        PolicyDto policyDto = modelMapper.map(policy, PolicyDto.class);
              return new ResponseEntity<>(policyDto, HttpStatus.OK);
    }

    @GetMapping("public/properties")
    public ResponseEntity<List<PropertyDto>> getAllProperties(@RequestParam boolean pending,
                                                              @RequestParam boolean completedRegistration) {
        List<Property> properties = propertyService.getAllProperties(pending, completedRegistration);
        List<PropertyDto> propertyDtoList = getPropertyDtoList(properties);
        return new ResponseEntity<>(propertyDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("protected/properties/users")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesOfUser() {
        List<Property> properties = propertyService.getAllPropertiesOfUSer();
        List<PropertyDto> propertyDtoList = getPropertyDtoList(properties);
        return new ResponseEntity<>(propertyDtoList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("protected/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> approveProperty(@PathVariable String propertyId) {
        propertyService.approveProperty(propertyId);
        message = "Property approved successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PatchMapping("protected/registration/properties/{propertyId}")
    public ResponseEntity<ResponseMessage> completeRegistration(@PathVariable String propertyId) {
        propertyService.completeRegistration(propertyId);
        message = "Property registration completed successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    private List<PropertyDto> getPropertyDtoList(List<Property> properties) {
        return properties.stream().map(property -> {
            List<FileInfo> fileInfos = getFileInfoList(property);
            PropertyContactDetailsDto contactDetailsDto = this.modelMapper.map(property.getPropertyContactDetails(), PropertyContactDetailsDto.class);
            CustomDto cityDto = this.modelMapper.map(property.getPropertyAddress().getCity(), CustomDto.class);
            PropertyAddressDto addressDto = new PropertyAddressDto(property.getPropertyAddress().getId(), property.getPropertyAddress().getStreetAddress(),
                    property.getPropertyAddress().getAddressLine2(), property.getPropertyAddress().getCode(), cityDto);
            return new PropertyDto(property.getId(), property.getName(), property.getRating(),
                    property.isPending(), property.isCompletedRegistration(), contactDetailsDto, addressDto, fileInfos);
        }).collect(Collectors.toList());
    }

    private List<FileInfo> getFileInfoList(Property property) {
        return fileStorageService.loadAll(property.getId()).map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", property.getId(), path.getFileName().toString()).build().toString();
            return new FileInfo(filename, url);
        }).collect(Collectors.toList());
    }
}
