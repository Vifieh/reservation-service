package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyAddress;
import com.reservation.reservationservice.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    Optional<Property> findByName(String name);

    List<Property> findByPendingAndCompletedRegistrationOrderByRatingDesc(boolean pending, boolean completedRegistration);

    List<Property> findByUserAndPending(User user, boolean pending);
}
