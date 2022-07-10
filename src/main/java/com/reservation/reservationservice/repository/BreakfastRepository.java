package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Breakfast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BreakfastRepository extends JpaRepository<Breakfast, String> {

}
