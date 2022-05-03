package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CancellationPolicy {

    @Id
    private String id;
    private String day;
    private String penalty;
}
