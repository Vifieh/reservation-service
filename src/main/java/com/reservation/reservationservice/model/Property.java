package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Property extends BaseEntity{

    @Id
    private String id;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "propertyType_id")
    private PropertyType propertyType;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> rooms;
}
