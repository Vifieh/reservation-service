package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vifieh Ruth
 * on 5/24/22
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, String> {
}
