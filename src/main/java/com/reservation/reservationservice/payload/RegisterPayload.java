package com.reservation.reservationservice.payload;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPayload {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String role;
}