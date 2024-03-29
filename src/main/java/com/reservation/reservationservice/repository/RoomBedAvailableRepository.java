package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.RoomBedAvailableKey;
import com.reservation.reservationservice.model.RoomBedAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBedAvailableRepository extends JpaRepository<RoomBedAvailable, RoomBedAvailableKey> {

}
