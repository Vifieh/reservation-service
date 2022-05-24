package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.PropertyAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PropertyAddressRepository extends JpaRepository<PropertyAddress, String> {

}
