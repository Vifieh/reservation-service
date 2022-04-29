package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City extends BaseEntity {

    @Id
    private String id;

    private String name;
}
