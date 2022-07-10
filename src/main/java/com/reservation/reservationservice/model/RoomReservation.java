package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.service.PropertyService;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoomReservation extends BaseEntity{

    @Id
    private String id;

    private Date checkIn;
    private Date checkOut;
    private double totalPrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private String specialRequest;
    private String arrivalTime;
    private String ref;
    private boolean hasPayed = false;
    @Column(nullable=false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean hasCheckedOut = false;
    private String checkedOutBy;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(mappedBy = "roomReservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ReservationContactDetails contactDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "property_id")
    Property property;

    @OneToMany(mappedBy = "roomReservation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoomReservationItem> roomReservationItemList;
}
