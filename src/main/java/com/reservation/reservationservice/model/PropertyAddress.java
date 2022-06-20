package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PropertyAddress extends BaseEntity{

    @Id
    private String id;
    private String streetAddress;
    private String addressLine2;
    private String code;

    @OneToOne(mappedBy = "propertyAddress")
    Property property;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}