package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Guest extends BaseEntity{

    @Id
    private String id;

    @Size(min = 3, message = "Country should have atleast 3 characters")
    private String name;

    @OneToMany(mappedBy = "guest")
    List<GuestExtraBed> guestExtraBeds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
