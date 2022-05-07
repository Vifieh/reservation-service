package com.reservation.reservationservice.dto;

import lombok.Data;

@Data
public class PropertyAddressDto {
    private String id;
    private String streetAddress;
    private String addressLine2;
    private String code;
    private CustomDto countryDto;
    private CustomDto cityDto;
}