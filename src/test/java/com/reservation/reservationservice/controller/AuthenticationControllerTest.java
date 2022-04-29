package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.payload.RegisterPayload;
import com.reservation.reservationservice.service.serviceImpl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @Test
    void registerUser_should_call_authenticationService() {
        RegisterPayload registerPayload = new RegisterPayload();
        ResponseEntity responseEntity = authenticationController.registerUser(registerPayload);
        verify(authenticationService).register(registerPayload);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }
}