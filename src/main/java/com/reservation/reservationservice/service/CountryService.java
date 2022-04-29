package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Country;

import java.util.List;

public interface CountryService {

    void createCountry(Country country);

    void editCountry(String countryId, Country country);

    List<Country> getAllCountries();

    Country getCountry(String countryId);

    void deleteCountry(String countryId);

}
