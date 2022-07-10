package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 5/13/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExtraBed {

    @Id
    private String id;
    private int numberOfExtraBeds;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @OneToMany(mappedBy = "extraBed")
    List<GuestExtraBed> guestExtraBeds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
