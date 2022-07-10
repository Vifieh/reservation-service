package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.constants.Policy;
import com.reservation.reservationservice.constants.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room extends BaseEntity {

    @Id
    private String id;
    private String name;
    private int numberOfRooms;
    private double roomSize;
    private double unitPrice;
    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Policy smokingPolicy;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roomName_id", referencedColumnName = "id")
    RoomName roomName;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RoomBedAvailable> roomBedAvailables;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<RoomReservationItem> roomReservationItemList;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rooms_amenities", joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id", referencedColumnName = "id"))
    private List<Amenity> amenities;

}