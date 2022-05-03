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
public class Facility {

    @Id
    private String id;
    private String  name;
    private Available available;

}
