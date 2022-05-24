package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.GuestExtraBed;
import com.reservation.reservationservice.model.GuestExtraBedKey;
import com.reservation.reservationservice.model.PropertyFacility;
import com.reservation.reservationservice.model.PropertyFacilityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestExtraBedRepository extends JpaRepository<GuestExtraBed, GuestExtraBedKey> {

}
