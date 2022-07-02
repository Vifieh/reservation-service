package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.RoomReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */

@Repository
public interface RoomReservationItemRepository extends JpaRepository<RoomReservationItem, String> {
}
