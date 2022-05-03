package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.CategoryAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryAmenityRepository extends JpaRepository<CategoryAmenity, String> {

    Optional<CategoryAmenity> findByName(String name);
}
