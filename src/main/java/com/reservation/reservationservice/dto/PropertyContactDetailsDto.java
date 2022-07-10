package com.reservation.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyContactDetailsDto {

    private String id;
    private String name;
    private String phoneNumber;
    private String alternativeNumber;
    private String companyName;
}
