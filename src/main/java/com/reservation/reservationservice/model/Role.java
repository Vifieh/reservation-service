package com.reservation.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reservation.reservationservice.constants.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> user;
}
