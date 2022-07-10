package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Available;
import com.reservation.reservationservice.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakfastPayload {

    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Available available;

    private List<DefaultPayload> breakfastAvailablePayload;

}

