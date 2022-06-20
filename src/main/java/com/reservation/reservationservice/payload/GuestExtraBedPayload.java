package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestExtraBedPayload {

        private String  guestId;
        private String range;
        private int unitPrice;

        @Enumerated(EnumType.STRING)
        private Currency currency;
    }