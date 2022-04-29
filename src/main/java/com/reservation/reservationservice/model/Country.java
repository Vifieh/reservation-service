package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country extends BaseEntity{

    @Id
    private String id;

    @Size(min = 3, message = "Country should have atleast 3 characters")
    private String name;
}
