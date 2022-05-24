package com.reservation.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 5/24/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentOption {
    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "languages")
    @JsonIgnore
    List<Property> properties;
}
