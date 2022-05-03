package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Available;
import com.reservation.reservationservice.constants.Site;
import com.reservation.reservationservice.constants.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Parking {

    @Id
    private String id;
    private Available available;
    private Type type;
    private Site site;
    private double unitPrice;
}
