package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.PaymentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Vifieh Ruth
 * on 7/7/22
 */
@Repository
public interface TransactionRepository extends JpaRepository<PaymentResponse, Long> {
    Optional<PaymentResponse> findByRef(String ref);
}
