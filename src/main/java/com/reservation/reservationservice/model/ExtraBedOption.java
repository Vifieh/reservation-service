package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Vifieh Ruth
 * on 5/13/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExtraBedOption {

    @Id
    private String id;
    private int numberOfExtraBeds;
}
