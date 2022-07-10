package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationContactDetailsPayload {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String countryId;
}
