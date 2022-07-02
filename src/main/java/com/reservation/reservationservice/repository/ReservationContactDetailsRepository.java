package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.ReservationContactDetails;
import com.reservation.reservationservice.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@Repository
public interface ReservationContactDetailsRepository extends JpaRepository<ReservationContactDetails, String> {
}
