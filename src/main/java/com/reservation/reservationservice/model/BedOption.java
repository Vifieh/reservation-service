package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BedOption {

    @EmbeddedId
    private BedOptionKey id;

    @ManyToOne
    @MapsId("bedAvailableId")
    @JoinColumn(name = "bedAvailable_Id")
    private BedAvailable bedAvailable;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    private Room room;

    private int numberOfBeds;
    private int numberOfGuests;
}