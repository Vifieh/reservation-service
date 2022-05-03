package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.serviceImpl.CountryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {
    @InjectMocks
    CountryController countryController;

    @Mock
    CountryServiceImpl countryService;

    CustomPayload countryPayload;
    Country country;

//    @Test
//    void createCountry_should_countryService() {
//        countryPayload = new CustomPayload();
//        country = new Country();
//        ResponseEntity responseEntity = countryController.createCountry(countryPayload);
//        verify(countryService).createCountry(country);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }
}