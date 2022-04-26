package com.reservation.reservationservice.service;

import com.reservation.reservationservice.dto.LoginDTO;
import com.reservation.reservationservice.dto.SignInDTO;
import com.reservation.reservationservice.model.RefreshToken;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.payload.RegisterPayload;

import java.util.Optional;

public interface AuthenticationService {

    void register(RegisterPayload registerPayload);

    LoginDTO login(User user);

//    Optional<RefreshToken> findByToken(String token);

    RefreshToken createRefreshToken(String userId);
//
//    RefreshToken verifyExpiration(RefreshToken token);
//
    String confirmToken(String token);
//
//    void resendVerification(String email);


}
