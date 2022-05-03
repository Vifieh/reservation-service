package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {

    @Id
    private String id;
    private String name;
    private String roomSize;
    private double unitPrice;
    private String numberOfRooms;
    private String smokingPolicy;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
