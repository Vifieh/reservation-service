package com.reservation.reservationservice.model;

import com.reservation.reservationservice.constants.Available;
import com.reservation.reservationservice.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Breakfast extends BaseEntity{

    @Id
    private String id;
    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Available available;

    @OneToOne
    @JoinColumn(name = "property_id")
    Property property;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "breakfast_breakfastAvailable", joinColumns = @JoinColumn(name = "breakfast_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "breakfastAvailable_id", referencedColumnName = "id"))
    private List<BreakfastAvailable> breakfastAvailableList;
}
