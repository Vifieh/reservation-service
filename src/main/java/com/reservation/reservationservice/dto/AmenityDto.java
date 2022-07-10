package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vifieh Ruth
 * on 6/4/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityDto extends BaseEntity {

    private String id;
    private String name;
    private boolean mostRequested;
}
