package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GuestExtraBedKey implements Serializable {

    @Column(name = "guest_id")
    private String guestId;

    @Column(name = "extraBed_id")
    private String extraBedId;

}