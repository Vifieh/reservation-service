package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReservationContactDetails {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "roomReservation_id")
    private RoomReservation roomReservation;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;
}
