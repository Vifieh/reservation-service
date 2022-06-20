package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Parking extends BaseEntity{

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Available available;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Site site;

    @Enumerated(EnumType.STRING)
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private double unitPrice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
