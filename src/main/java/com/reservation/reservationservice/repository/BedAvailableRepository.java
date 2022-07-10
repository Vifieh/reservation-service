package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.BedAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BedAvailableRepository extends JpaRepository<BedAvailable, String> {

    Optional<BedAvailable> findByName(String name);
}
