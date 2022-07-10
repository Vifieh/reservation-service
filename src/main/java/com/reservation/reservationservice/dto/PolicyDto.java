package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Vifieh Ruth
 * on 7/1/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyDto {

    private String id;
    private String guestCanCancel;
    private String guestPay;
    private String checkInFrom;
    private String checkInTo;
    private String checkOutFrom;
    private String checkOutTo;

    @Enumerated(EnumType.STRING)
    private Default canAccommodateChildren;
}
