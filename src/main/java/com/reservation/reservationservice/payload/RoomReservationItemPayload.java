package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author Vifieh Ruth
 * on 6/26/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationItemPayload {

    private String roomId;
    private int numberOfRooms;
    private double price;
    private String fullGuestName;
    private String guestEmail;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}