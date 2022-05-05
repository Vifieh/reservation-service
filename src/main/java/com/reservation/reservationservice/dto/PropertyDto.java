package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BaseEntity;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.Country;
import lombok.Data;

@Data
public class PropertyDto extends BaseEntity {

    private String id;
    private int rating;
    private PropertyContactDetailsDto propertyContactDetailsDto;
    private PropertyAddressDto propertyAddressDto;
}

@Data
class PropertyContactDetailsDto {

    private String id;
    private String name;
    private String phoneNumber;
    private String alternativeNumber;
    private String companyName;
}

@Data
class PropertyAddressDto {

    private String id;
    private String address;
    private Country country;
    private City city;
}
