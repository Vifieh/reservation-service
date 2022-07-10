package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.PropertyTypeDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.SuccessResponse;
import com.reservation.reservationservice.model.PropertyType;
import com.reservation.reservationservice.payload.PropertyTypePayload;
import com.reservation.reservationservice.service.PropertyTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/protected")
@CrossOrigin
@RestController
public class PropertyTypeController {

    @Autowired
    PropertyTypeService propertyTypeService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/propertyTypes")
    public ResponseEntity<SuccessResponse> createPropertyType(@RequestBody @Valid
                                                                          PropertyTypePayload propertyTypePayload) {
        PropertyType propertyType = convertPropertyTypePayloadToPropertyType(propertyTypePayload);
        propertyTypeService.createPropertyType(propertyType);
        message = "PropertyType created successfully!";
        return new ResponseEntity<>(new SuccessResponse(message, propertyType.getId()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("protected/propertyTypes/{propertyTypeId}")
    public ResponseEntity<ResponseMessage> editPropertyType(@PathVariable("propertyTypeId") String propertyTypeId,
                                                            @RequestBody @Valid PropertyTypePayload propertyTypePayload) {
        PropertyType propertyType = convertPropertyTypePayloadToPropertyType(propertyTypePayload);
        propertyTypeService.editPropertyType(propertyTypeId, propertyType);
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("propertyTypes")
    public ResponseEntity<List<PropertyTypeDto>> getPropertyTypes() {
        List<PropertyType> propertyTypes = propertyTypeService.getAllPropertyTypes();
        List<PropertyTypeDto> propertyTypeDtos = propertyTypes.stream().map(propertyType ->
                this.modelMapper.map(propertyType, PropertyTypeDto.class)).collect(Collectors.toList());
        message = "PropertyType created successfully!";
        return new ResponseEntity<>(propertyTypeDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("propertyTypes/{propertyTypeId}")
    public ResponseEntity<PropertyTypeDto> getPropertyType(@PathVariable("propertyTypeId") String propertyTypeId) {
        PropertyType propertyType = propertyTypeService.getPropertyType(propertyTypeId);
        PropertyTypeDto propertyTypeDto = this.modelMapper.map(propertyType, PropertyTypeDto.class);
        return new ResponseEntity<>(propertyTypeDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("protected/propertyTypes/{propertyTypeId}")
    public ResponseEntity<ResponseMessage> deletePropertyType(@PathVariable("propertyTypeId") String propertyTypeId) {
        propertyTypeService.deletePropertyType(propertyTypeId);
        message = "PropertyType deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.NO_CONTENT);
    }

    private PropertyType convertPropertyTypePayloadToPropertyType(PropertyTypePayload propertyTypePayload) {
        return this.modelMapper.map(propertyTypePayload, PropertyType.class);
    }

}
