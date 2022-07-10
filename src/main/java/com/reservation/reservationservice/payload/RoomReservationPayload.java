package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationPayload {

    private Date checkIn;
    private Date checkOut;
    private double totalPrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private String specialRequest;
    private String arrivalTime;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private ReservationContactDetailsPayload contactDetailsPayload;
    private List<RoomReservationItemPayload> reservationItemPayloadList;
}