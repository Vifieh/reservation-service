package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyContactDetailsPayload {
    private String name;
    private String phoneNumber;
    private String alternativeNumber;
    private String companyName;
}
