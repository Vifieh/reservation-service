package com.reservation.reservationservice.model;

import com.reservation.reservationservice.dto.CustomDto;
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
    @JoinColumn(name = "property_id")
    Property property;
}