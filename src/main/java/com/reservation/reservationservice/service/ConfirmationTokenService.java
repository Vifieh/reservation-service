package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken confirmationToken);

    Optional<ConfirmationToken> getToken(String token);

    int setConfirmedAt(String token);
}
