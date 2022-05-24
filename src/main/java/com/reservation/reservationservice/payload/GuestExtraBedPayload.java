package com.reservation.reservationservice.payload;

import lombok.Data;

@Data
public class GuestExtraBedPayload {

        private String  guestId;
        private String range;
        private int unitPrice;
    }