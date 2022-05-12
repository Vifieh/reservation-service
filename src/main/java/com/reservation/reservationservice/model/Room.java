package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.constants.Policy;
import com.reservation.reservationservice.constants.Size;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room extends BaseEntity{

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "roomName_id", referencedColumnName = "id")
    RoomName roomName;

    @OneToMany(mappedBy = "room")
    List<RoomBedAvailable> roomBedAvailables;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
