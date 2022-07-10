package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.RoomBedAvailableKey;
import lombok.*;

/**
 * @author Vifieh Ruth
 * on 6/13/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBedAvailableDto {

    private RoomBedAvailableKey id;
    private BedAvailableDto bedAvailableDto;
    private int numberOfBeds;
}