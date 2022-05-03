package com.reservation.reservationservice.service;

import com.reservation.reservationservice.dto.LoginDto;
import com.reservation.reservationservice.model.RefreshToken;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.payload.RegisterPayload;

import java.util.Optional;

public interface AuthenticationService {

    void register(RegisterPayload registerPayload);

    void resendVerification(String email);

    LoginDto login(User user);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(String userId);

    RefreshToken verifyExpiration(RefreshToken token);

    String confirmToken(String token);

    User checkEmail(String email);
}
