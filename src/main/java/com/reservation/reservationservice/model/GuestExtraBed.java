package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GuestExtraBed {

    @EmbeddedId
    private GuestExtraBedKey id;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String range;
    private int unitPrice;

    @ManyToOne
    @MapsId("guestId")
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @MapsId("extraBedId")
    @JoinColumn(name = "extraBed_id")
    private ExtraBed extraBed;


}