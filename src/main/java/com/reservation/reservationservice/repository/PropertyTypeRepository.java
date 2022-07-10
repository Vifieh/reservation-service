package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, String> {

    Optional<PropertyType> findByName(String name);
}
