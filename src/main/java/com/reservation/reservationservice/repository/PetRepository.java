package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vifieh Ruth
 * on 5/24/22
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, String> {
}
