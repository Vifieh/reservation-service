package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.PaymentOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentOptionRepository extends JpaRepository<PaymentOption, String> {

    Optional<PaymentOption> findByName(String name);
}
