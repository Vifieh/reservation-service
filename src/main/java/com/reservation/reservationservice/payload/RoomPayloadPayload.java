package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Policy;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class RoomPayloadPayload {
    private CustomPayload roomTypePayload;
    private CustomPayload roomNamePayload;
    private String name;
    private int numberOfRoomsOfThisType;

    @Enumerated(EnumType.STRING)
    private Policy smokingPolicy;

}

