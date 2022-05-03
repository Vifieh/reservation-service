package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(ERole role);
}
