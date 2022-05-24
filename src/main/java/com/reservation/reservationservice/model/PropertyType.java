package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PropertyType extends BaseEntity{

    @Id
    private String id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "propertyType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Property> properties;
}
