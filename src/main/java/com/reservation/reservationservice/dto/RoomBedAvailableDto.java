package com.reservation.reservationservice.dto;

import lombok.*;

/**
 * @author Vifieh Ruth
 * on 6/13/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBedAvailableDto {

    private String id;
    private String bedAvailableId;
    private int numberOfBeds;
}