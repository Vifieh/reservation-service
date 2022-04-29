package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.City;

import java.util.List;

public interface CityService {

    void createCity(String countryId, City city);

    void editCity(String cityId, City city);

    List<City> getAllCities();

    City getCity(String cityId);

    void deleteCity(String cityId);
}
