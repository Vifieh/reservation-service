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
    private String name;
    private int rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "propertyContactDetails_id", referencedColumnName = "id")
    private PropertyContactDetails propertyContactDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "propertyAdress_id", referencedColumnName = "id")
    private PropertyAddress propertyAddress;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyType_id")
    private PropertyType propertyType;
}
