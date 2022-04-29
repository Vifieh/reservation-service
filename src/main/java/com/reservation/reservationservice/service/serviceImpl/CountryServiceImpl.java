package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.CountryRepository;
import com.reservation.reservationservice.service.AuthenticationService;
import com.reservation.reservationservice.service.CountryService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final Util util = new Util();

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    UserService userService;

    @Override
    public void createCountry(Country country) {
        User user = userService.getAuthUser();
        Optional<Country> country1 = countryRepository.findByName(country.getName());
        if(country1.isPresent()) {
            throw new ResourceAlreadyExistException("Country already exist");
        }
        country.setId(util.generateId());
        country.setUser(user);
        country.setCreatedBy(user.getEmail());
        countryRepository.save(country);
    }

    @Override
    public void editCountry(String countryId, Country country) {
        User user = userService.getAuthUser();
            Country country1 = getCountry(countryId);
            country1.setName(country.getName());
            country1.setUser(user);
            country1.setCreatedBy(user.getEmail());
            countryRepository.save(country1);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountry(String countryId) {
        Optional<Country> country = countryRepository.findById(countryId);
        country.orElseThrow(() -> new ResourceNotFoundException("Country not found with id - " + countryId));
        return country.get();
    }

    @Override
    public void deleteCountry(String countryId) {
        countryRepository.deleteById(countryId);
    }
}
