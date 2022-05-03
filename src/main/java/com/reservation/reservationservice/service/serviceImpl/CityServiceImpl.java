package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.CityRepository;
import com.reservation.reservationservice.service.CityService;
import com.reservation.reservationservice.service.CountryService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private Util util = new Util();

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserService userService;

    @Autowired
    CountryService countryService;

    @Override
    public void createCity(String countryId, City city) {
        User user = userService.getAuthUser();
        Country country = countryService.getCountry(countryId);
        city.setId(util.generateId());
        city.setUser(user);
        city.setCountry(country);
        city.setCreatedBy(user.getEmail());
        cityRepository.save(city);
    }

    @Override
    public void editCity(String cityId, City city) {
        User user = userService.getAuthUser();
        City city1 = getCity(cityId);
        city1.setName(city.getName());
        city1.setUser(user);
        city1.setCreatedBy(user.getEmail());
        cityRepository.save(city1);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCity(String cityId) {
        Optional<City> city = cityRepository.findById(cityId);
        city.orElseThrow(() -> new ResourceNotFoundException("City not found with id - "+ cityId));
        return city.get();
    }

    @Override
    public void deleteCity(String cityId) {
        cityRepository.deleteById(cityId);
    }
}
