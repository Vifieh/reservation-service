package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.*;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 7/1/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakfastDto {
    private String id;
    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Available available;

    private List<CustomDto> breakfastAvailableDto;
}