package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 5/15/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAmenityPayload {
    private String amenityId;
    private  List<DefaultPayload> rooms;
}
