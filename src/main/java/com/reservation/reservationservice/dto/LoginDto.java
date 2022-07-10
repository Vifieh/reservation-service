package com.reservation.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginDto {

    private final String accessToken;
    private final String refreshToken;
    private final String id;
    private final Instant expiredIn;
    private final String email;
    private final List<String> role;
    private String type = "Bearer";

    public LoginDto(String accessToken, String refreshToken, String id, Instant expiredIn, String email, List<String> role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.expiredIn = expiredIn;
        this.email = email;
        this.role = role;
    }
}