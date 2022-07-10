package com.reservation.reservationservice.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.reservation.reservationservice.exception.BadRequestException;
import com.reservation.reservationservice.payload.RegisterPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Test
    public void testRegister() {
        AuthenticationServiceImpl authenticationServiceImpl = new AuthenticationServiceImpl();
        RegisterPayload registerPayload = mock(RegisterPayload.class);
        when(registerPayload.getEmail()).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> authenticationServiceImpl.register(registerPayload));
        verify(registerPayload).getEmail();
    }
}

