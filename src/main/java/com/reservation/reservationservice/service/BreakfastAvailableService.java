package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.BreakfastAvailable;

import java.util.List;

public interface BreakfastAvailableService {

    void createBreakfastAvailable(BreakfastAvailable breakfastAvailable);

    void editBreakfastAvailable(String breakfastId, BreakfastAvailable breakfastAvailable);

    List<BreakfastAvailable> getAllBreakfastAvailable();

    BreakfastAvailable getBreakfastAvailable(String breakfastId);

    void deleteBreakfastAvailable(String breakfastId);
}
