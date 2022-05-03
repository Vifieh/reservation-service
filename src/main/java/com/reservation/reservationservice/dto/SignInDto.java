package com.reservation.reservationservice.dto;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SignInDto {

        private final String accessToken;
        private final String refreshToken;
        private final String id;
        private final String number;
        private final String email;
        private final List<String> role;
        private String type = "Bearer";
}
