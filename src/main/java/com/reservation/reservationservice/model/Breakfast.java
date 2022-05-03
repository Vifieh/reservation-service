package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Available;
import com.reservation.reservationservice.dto.BreakfastAvailableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Breakfast {

    @Id
    private String id;
    private Available available;
    private double unitPrice;
}
