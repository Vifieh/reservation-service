package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.BreakfastAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BreakfastAvailableRepository extends JpaRepository<BreakfastAvailable, String> {

    Optional<BreakfastAvailable> findByName(String name);
}
