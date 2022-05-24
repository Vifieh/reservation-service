package com.reservation.reservationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.TokenRefreshException;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.payload.LoginPayload;
import com.reservation.reservationservice.payload.RegisterPayload;
import com.reservation.reservationservice.payload.TokenRefreshPayload;
import com.reservation.reservationservice.service.serviceImpl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void confirm_should_call_authenticationService() {
        String token = "token";
        ResponseEntity responseEntity = authenticationController.confirm(token);
        verify(authenticationService).confirmToken(token);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void resendVerification_should_call_authenticationService() {
        String email = "test@gmail.com";
        ResponseEntity responseEntity = authenticationController.resendVerification(email);
        verify(authenticationService).resendVerification(email);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

//    @Test
//    void loginUser_should_call_authenticationService() throws Exception {
//        LoginPayload loginPayload = new LoginPayload("test@gmail.com", "password");
////        loginPayload.setEmail("test@gmail.com");
////        loginPayload.setPassword("password");
////        new ObjectMapper().writeValueAsString(loginPayload);
//        User user = new User();
//        user.setEmail(loginPayload.getEmail());
//        user.setPassword(loginPayload.getPassword());
//        ResponseEntity responseEntity = authenticationController.login(loginPayload);
//        verify(authenticationService).login(user);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }

    @Test
    void refreshToken_should_call_authenticationService() {
        String token = "0223a1ae-6bda-4978-ab3d-0645e041c2d3";
        TokenRefreshPayload refreshPayload = new TokenRefreshPayload();
        refreshPayload.setRefreshToken(token);
//        ResponseEntity responseEntity = authenticationController.refreshToken(refreshPayload);
//        verify(authenticationService).findByToken(token).map(authenticationService::verifyExpiration);
        TokenRefreshException exception = assertThrows(TokenRefreshException.class, () -> this.authenticationController.refreshToken(refreshPayload));
        assertEquals( exception.getMessage(), "Failed for ["+token+"]: Refresh token is not in database!");
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

    }
}