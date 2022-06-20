package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.SuccessResponse;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.service.serviceImpl.PropertyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Vifieh Ruth
 * on 5/16/22
 */
@ExtendWith(MockitoExtension.class)
@Slf4j
class PropertyControllerTest {
    
    @InjectMocks
    PropertyController propertyController;

    @Mock
    PropertyServiceImpl propertyService;

    @MockBean
    private ModelMapper mockModelMapper;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    void createProperty_should_call_propertyService() {
//        PropertyPayload propertyPayload = new PropertyPayload();
//        String propertyTypeId = "1lds";
//        Property property = mockModelMapper.map(propertyPayload, Property.class);
//        ResponseEntity responseEntity = propertyController.createProperty(propertyTypeId, propertyPayload);
//        verify(propertyService).saveProperty(propertyTypeId, property);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//        assertThat(responseEntity.getBody())
//                .isEqualTo(new SuccessResponse("Property crested successfully!", property.getId()));
//    }

//    @Test
//    void adFacilities_should_call_propertyService() {
//        List<PropertyFacilityPayload> propertyFacilityPayloads = new ArrayList<>();
//        String propertyId = "1lds";
//        ResponseEntity responseEntity = propertyController.addFacilities(propertyId, propertyFacilityPayloads);
//        verify(propertyService).addFacilities(propertyId, propertyFacilityPayloads);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }
//
//    @Test
//    void addBreakfast_should_call_propertyService() {
//        BreakfastPayload breakfastPayload = new BreakfastPayload();
//        String propertyId = "1lds";
//        ResponseEntity responseEntity = propertyController.addBreakfast(propertyId, breakfastPayload);
//        verify(propertyService).addBreakfast(propertyId, breakfastPayload);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }

//    @Test
//    void addExtraBedOption_should_call_propertyService() {
//        ExtraBedPayload extraBedPayload = new ExtraBedPayload();
//        String propertyId = "1lds";
//        ResponseEntity responseEntity = propertyController.addExtraBedOption(propertyId, extraBedPayload);
//        verify(propertyService).addExtraBedOption(propertyId, extraBedPayload);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }

    @Test
    void addPolicy_should_call_propertyService() {
        PolicyPayload policyPayload = new PolicyPayload();
        String propertyId = "1lds";
        ResponseEntity responseEntity = propertyController.addPolicy(propertyId, policyPayload);
        verify(propertyService).addPolicy(propertyId, policyPayload);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

//    @Test
//    void addPaymentOptions_should_call_propertyService() {
//        List<PaymentOption> paymentOptionPayload = new ArrayList<>();
//        String propertyId = "1lds";
//        ResponseEntity responseEntity = propertyController.addPaymentOptions(propertyId, paymentOptionPayload);
//        verify(propertyService).addPaymentOption(propertyId, paymentOptionPayload);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }
}