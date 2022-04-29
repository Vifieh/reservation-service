package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CityRepository extends JpaRepository<City, String> {

    Optional<City> findByName(String name);
}
