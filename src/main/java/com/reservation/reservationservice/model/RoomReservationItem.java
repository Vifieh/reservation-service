package com.reservation.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reservation.reservationservice.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomReservationItem extends BaseEntity{

    @Id
    private String id;

    private int numberOfRooms;
    private double price;
    private String fullGuestName;
    private String guestEmail;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room;

    @ManyToOne
    @JoinColumn(name = "roomReservation_id")
    private RoomReservation roomReservation;
}
