package com.reservation.reservationservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BedOptionKey implements Serializable {

    @Column(name = "bedAvailable_id")
    private String bedAvailableId;

    @Column(name = "room_id")
    private String roomId;

}