package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.UserContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactDetailsRepository extends JpaRepository<UserContactDetails, String> {

}
