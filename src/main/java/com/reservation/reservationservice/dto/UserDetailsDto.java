package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BaseEntity;
import com.reservation.reservationservice.model.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto extends BaseEntity {
    private String id;
    private String email;
    private ContactDetailsDto contactDetailsDto;
    private List<Role> role;
}
