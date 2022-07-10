package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PropertyContactDetails extends BaseEntity{

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String alternativeNumber;
    private String companyName;

    @OneToOne(mappedBy = "propertyContactDetails")
    Property property;
}
