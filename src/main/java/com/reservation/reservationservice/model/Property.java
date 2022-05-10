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

    @OneToOne(mappedBy = "property")
    Parking parking;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "propertyType_id")
    private PropertyType propertyType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "property_language", joinColumns = @JoinColumn(name = "property_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))
    private List<Language> languages;
}
