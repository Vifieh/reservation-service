package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.Country;
import lombok.Data;

@Data
public class PropertyAddressDto {

    private String id;
    private String address;
    private Country country;
    private City city;
}
