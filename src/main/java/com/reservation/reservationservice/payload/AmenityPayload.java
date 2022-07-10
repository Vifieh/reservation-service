package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vifieh Ruth
 * on 6/4/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityPayload {

    private String name;
    private boolean mostRequested = false;
}
