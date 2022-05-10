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
public class Facility extends BaseEntity{

    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
