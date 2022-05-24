package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.ExtraBed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExtraBedRepository extends JpaRepository<ExtraBed, String> {

}
