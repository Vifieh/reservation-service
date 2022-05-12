package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.constants.Policy;
import com.reservation.reservationservice.constants.Size;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class RoomPayload {
    private String name;
    private int numberOfRooms;
    private double roomSize;
    private double unitPrice;
    private int numberOfGuests;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Policy smokingPolicy;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private List<RoomBedAvailablePayload> roomBedAvailablePayloadList;


}

