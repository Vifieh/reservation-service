package com.reservation.reservationservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country extends BaseEntity{

    @Id
    private String id;

    @Size(min = 3, message = "Country should have atleast 3 characters")
    @ApiModelProperty(notes ="Country should have atleast 3 characters")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
