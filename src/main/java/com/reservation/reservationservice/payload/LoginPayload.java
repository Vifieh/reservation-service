package com.reservation.reservationservice.payload;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginPayload {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
