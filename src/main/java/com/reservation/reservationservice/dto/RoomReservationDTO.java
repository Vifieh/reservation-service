package com.reservation.reservationservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationDTO {
    private String id;
    private double totalPrice;
    private String ref;
}