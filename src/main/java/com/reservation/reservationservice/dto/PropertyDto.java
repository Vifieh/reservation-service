package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BaseEntity;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.Country;
import lombok.Data;

@Data
public class PropertyDto extends BaseEntity {

    private String id;
    private String name;
    private int rating;
    private PropertyContactDetailsDto propertyContactDetailsDto;
    private PropertyAddressDto propertyAddressDto;
}

