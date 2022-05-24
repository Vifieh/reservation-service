package com.reservation.reservationservice.payload;

import lombok.Data;

@Data
public class PropertyContactDetailsPayload {
    private String name;
    private String phoneNumber;
    private String alternativeNumber;
    private String companyName;
}
