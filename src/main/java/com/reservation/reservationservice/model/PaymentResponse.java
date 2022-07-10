package com.reservation.reservationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Vifieh Ruth
 * on 7/7/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentResponse extends BaseEntity{
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ref;

    @Column
    private String zitopay_transaction_reference;
}
