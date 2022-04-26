package com.reservation.reservationservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenRefreshDTO {

    private final String accessToken;
    private final String refreshToken;
    private String tokenType = "Bearer";

}