package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyFacilityPayload {

    private String facilityId;

    @Enumerated(EnumType.STRING)
    private Status status;
}