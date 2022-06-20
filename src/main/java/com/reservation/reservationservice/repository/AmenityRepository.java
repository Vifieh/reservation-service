package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Amenity;
import com.reservation.reservationservice.model.CategoryAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, String> {

    Optional<Amenity> findByName(String name);

    List<Amenity> findAllByMostRequested(boolean mostRequested);

    List<Amenity> findByAndCategoryAmenityAndMostRequested(CategoryAmenity categoryAmenity, boolean mostRequested);
}
