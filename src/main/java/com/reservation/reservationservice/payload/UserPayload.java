package com.reservation.reservationservice.payload;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {
    private String email;
    private ContactDetailsPayload contactDetailsPayload;
}
