package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.ERole;
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
public class RoleDto {

    private String id;
    private ERole role;

}
