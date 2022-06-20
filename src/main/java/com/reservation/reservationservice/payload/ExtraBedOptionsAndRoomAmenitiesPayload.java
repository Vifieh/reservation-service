package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/18/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtraBedOptionsAndRoomAmenitiesPayload {

    private ExtraBedPayload extraBedPayload;
    private List<RoomAmenityPayload> roomAmenityPayloadList;
}
