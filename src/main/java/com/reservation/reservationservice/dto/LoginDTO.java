package com.reservation.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class LoginDTO {

    private final String accessToken;
    private final String refreshToken;
    private final String id;
    private final String email;
    private final List<String> role;
    private String type = "Bearer";

    public LoginDTO(String accessToken, String refreshToken, String id, String email, List<String> role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
        this.role = role;
    }
}