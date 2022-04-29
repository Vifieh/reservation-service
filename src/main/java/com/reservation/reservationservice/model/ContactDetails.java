package com.reservation.reservationservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ContactDetails {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    @OneToOne(mappedBy = "contactDetails")
    private User user;
}
