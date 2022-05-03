package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, String> {

    Optional<Amenity> findByName(String name);
}
