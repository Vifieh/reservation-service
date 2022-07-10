package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.RoomName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomNameRepository extends JpaRepository<RoomName, String> {

    Optional<RoomName> findByName(String name);
}
