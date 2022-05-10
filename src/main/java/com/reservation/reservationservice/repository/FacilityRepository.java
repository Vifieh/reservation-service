package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {

    Optional<Facility> findByName(String name);
}
