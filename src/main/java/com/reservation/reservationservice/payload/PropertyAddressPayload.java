package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyAddressPayload {
    private String streetAddress;
    private String addressLine2;
    private String code;
    private DefaultPayload cityPayload;
}