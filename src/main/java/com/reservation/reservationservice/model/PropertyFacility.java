package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PropertyFacility {

    @EmbeddedId
    private PropertyFacilityKey id;

    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @MapsId("facilityId")
    @JoinColumn(name = "facility_id")
    private Facility facility;

    private Status status;

}