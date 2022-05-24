package com.reservation.reservationservice.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsPayload {
    private String firstName;
    private String lastName;
    private String phoneNumber;

}