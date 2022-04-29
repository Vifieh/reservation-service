package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDTO extends BaseEntity {

    private String id;
    private String name;

}
