package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomName {

    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
}
