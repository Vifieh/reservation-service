package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    Optional<Room> findByName(String name);

    List<Room> findByUserAndProperty(User user, Property property);
}
