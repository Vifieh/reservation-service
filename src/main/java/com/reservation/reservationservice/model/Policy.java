package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Vifieh Ruth
 * on 5/23/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Policy {

    @Id
    private String id;
    private String guestCanCancel;
    private String guestPay;
    private String checkInFrom;
    private String checkInTo;
    private String checkOutFrom;
    private String checkOutTo;

    @Enumerated(EnumType.STRING)
    private Default canAccommodateChildren;

    @OneToOne
    @JoinColumn(name = "property_id")
    Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
