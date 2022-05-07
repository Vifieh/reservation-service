package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    Optional<Property> findByName(String name);
}
