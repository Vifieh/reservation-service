package com.reservation.reservationservice.payload;

import lombok.Data;

@Data
public class PropertyPayload {
    private String name;
    private int rating;
    private PropertyContactDetailsPayload propertyContactDetailsPayload;
    private PropertyAddressPayload propertyAddressPayload;
}

