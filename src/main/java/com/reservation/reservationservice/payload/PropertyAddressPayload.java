package com.reservation.reservationservice.payload;

import lombok.Data;

@Data
public class PropertyAddressPayload {
    private String streetAddress;
    private String addressLine2;
    private String code;
    private CustomPayload country;
    private CustomPayload city;
}