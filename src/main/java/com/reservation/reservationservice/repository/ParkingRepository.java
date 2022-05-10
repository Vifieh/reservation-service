package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {

}
