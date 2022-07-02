package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, String> {
    List<RoomReservation> findByPropertyOrderByCheckInDesc(Property property);
}
