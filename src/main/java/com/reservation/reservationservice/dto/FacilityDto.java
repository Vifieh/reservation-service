package com.reservation.reservationservice.dto;

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
public class FacilityDto extends BaseEntity {

    private String id;
    private String name;
    private boolean choice;
}
