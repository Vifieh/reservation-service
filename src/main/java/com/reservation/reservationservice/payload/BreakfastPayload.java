package com.reservation.reservationservice.payload;

import com.reservation.reservationservice.constants.Available;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class BreakfastPayload {

    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private Available available;

    private List<DefaultPayload> breakfastAvailablePayload;

}

