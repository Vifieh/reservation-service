package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {

    Optional<RoomType> findByName(String name);
}
