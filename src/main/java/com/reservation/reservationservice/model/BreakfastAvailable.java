package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BreakfastAvailable extends BaseEntity{

    @Id
    private String id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
