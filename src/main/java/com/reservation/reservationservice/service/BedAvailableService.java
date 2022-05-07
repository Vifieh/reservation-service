package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.BedAvailable;

import java.util.List;

public interface BedAvailableService {

    void createBedAvailable(BedAvailable bedAvailable);

    void editBedAvailable(String bedId, BedAvailable bedAvailable);

    List<BedAvailable> getAllBedsAvailable();

    BedAvailable getBedAvailable(String bedId);

    void deleteBedAvailable(String bedId);
}
