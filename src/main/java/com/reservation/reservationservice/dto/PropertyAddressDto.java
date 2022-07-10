package com.reservation.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyAddressDto {
    private String id;
    private String streetAddress;
    private String addressLine2;
    private String code;
    private CustomDto cityDto;
}