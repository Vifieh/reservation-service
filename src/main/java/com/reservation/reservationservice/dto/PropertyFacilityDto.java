package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.Status;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyFacilityDto {

    private FacilityDto facilityDto;

    @Enumerated(EnumType.STRING)
    private Status status;
}