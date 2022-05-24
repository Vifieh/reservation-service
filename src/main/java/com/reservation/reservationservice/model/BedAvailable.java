package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BedAvailable extends BaseEntity{

    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "bedAvailable")
    List<RoomBedAvailable> roomBedAvailables;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
