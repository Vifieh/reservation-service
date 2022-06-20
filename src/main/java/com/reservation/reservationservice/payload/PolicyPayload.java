package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Default;
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
public class PolicyPayload {
    private String guestCanCancel;
    private String guestPay;
    private String checkInFrom;
    private String checkInTo;
    private String checkOutFrom;
    private String checkOutTo;
    private Default canAccommodateChildren;
    private PetPayload petPayload;
}
