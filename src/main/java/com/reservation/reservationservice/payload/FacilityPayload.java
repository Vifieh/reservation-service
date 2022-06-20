package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vifieh Ruth
 * on 6/3/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityPayload {

    private String name;
    private boolean choice = false;
}
