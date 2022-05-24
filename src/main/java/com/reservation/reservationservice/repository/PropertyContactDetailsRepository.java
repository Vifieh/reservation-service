package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.PropertyContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PropertyContactDetailsRepository extends JpaRepository<PropertyContactDetails, String> {

}
