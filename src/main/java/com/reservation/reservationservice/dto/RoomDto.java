package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.constants.Policy;
import com.reservation.reservationservice.constants.Size;
import com.reservation.reservationservice.payload.RoomBedAvailablePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/13/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private String id;
    private String name;
    private int numberOfRooms;
    private double roomSize;
    private double unitPrice;
    private int numberOfGuests;
    private String roomName;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Policy smokingPolicy;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private List<RoomBedAvailableDto> roomBedAvailableDtoList;
}