package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PropertyFacilityKey implements Serializable {

    @Column(name = "property_id")
    private String propertyId;

    @Column(name = "facility_id")
    private String facilityId;

}