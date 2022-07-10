package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.model.GuestExtraBed;
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
public class ExtraBedPayload {

    private int numberOfExtraBeds;
    private List<GuestExtraBedPayload> guestExtraBedPayloadList;
}

