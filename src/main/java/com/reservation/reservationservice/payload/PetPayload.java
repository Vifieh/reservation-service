package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Default;
import com.reservation.reservationservice.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Vifieh Ruth
 * on 5/24/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetPayload {
    private Default allowPets;
    private Status charge;
}
