package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Default;
import com.reservation.reservationservice.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Vifieh Ruth
 * on 5/23/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pet {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Default allowPets;

    @Enumerated(EnumType.STRING)
    private Status charge;

    @OneToOne
    @JoinColumn(name = "property_id")
    Property property;
}
