package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.Country;
import lombok.Data;

@Data
public class PropertyPayload {
    private int rating;
    private PropertyContactDetailsDto propertyContactDetailsDto;
    private PropertyAddressDto propertyAddressDto;
}

@Data
class PropertyContactDetailsDto {
    private String name;
    private String phoneNumber;
    private String alternativeNumber;
    private String companyName;
}

@Data
class PropertyAddressDto {
    private String address;
    private Country country;
    private City city;
}
