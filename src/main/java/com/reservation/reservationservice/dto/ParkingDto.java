package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.*;
import lombok.*;

import javax.persistence.*;

/**
 * @author Vifieh Ruth
 * on 7/1/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDto {
    private String id;
    private Available available;
    private Type type;
    private Site site;
    private Reservation reservation;
    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private Currency currency;

}
