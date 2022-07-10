package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.SuccessResponse;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.service.serviceImpl.PropertyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


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


    @BeforeEach
    void setUp() {
    }

//    @Test
//    void createProperty_should_call_propertyService() {
//        PropertyPayload propertyPayload = new PropertyPayload();
//        String propertyTypeId = "1lds";
//        ResponseEntity<SuccessResponse> responseEntity = propertyController.createProperty(propertyTypeId, propertyPayload);
//        verify(propertyService).saveProperty(propertyTypeId, propertyPayload);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }

    @Test
    void addFacilityServices_should_call_propertyService() {
        FacilitiesServicesPayload facilitiesServicesPayload = new FacilitiesServicesPayload();
        String propertyId = "1lds";
        ResponseEntity responseEntity = propertyController.addFacilityServices(propertyId, facilitiesServicesPayload);
        verify(propertyService).addFacilityServices(propertyId, facilitiesServicesPayload);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void addPolicy_should_call_propertyService() {
        PolicyPayload policyPayload = new PolicyPayload();
        String propertyId = "1lds";
        ResponseEntity responseEntity = propertyController.addPolicy(propertyId, policyPayload);
        verify(propertyService).addPolicy(propertyId, policyPayload);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void addPaymentOptions_should_call_propertyService() {
        PaymentOptionWrapper paymentOptionWrapper = new PaymentOptionWrapper();
        String propertyId = "1lds";
        ResponseEntity responseEntity = propertyController.addPaymentOptions(propertyId, paymentOptionWrapper);
        verify(propertyService).addPaymentOptions(propertyId, paymentOptionWrapper.getPaymentOptionsPayload());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
}