package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.repository.CityRepository;
import com.reservation.reservationservice.service.CityService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private Util util = new Util();

    @Autowired
    CityRepository cityRepository;

    @Override
    public void createCity(String countryId, City city) {

    }

    @Override
    public void editCity(String cityId, City city) {

    }

    @Override
    public List<City> getAllCities() {
        return null;
    }

    @Override
    public City getCity(String cityId) {
        return null;
    }

    @Override
    public void deleteCity(String cityId) {

    }
}
