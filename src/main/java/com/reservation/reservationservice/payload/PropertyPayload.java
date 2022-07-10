package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyPayload {
    private String name;
    private int rating;
    private PropertyContactDetailsPayload propertyContactDetailsPayload;
    private PropertyAddressPayload propertyAddressPayload;
}

