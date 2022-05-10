package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomName extends BaseEntity{

    @Id
    private String id;
    private String name;

    @OneToOne(mappedBy = "roomName")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
}
