package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Facility;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyFacility;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.FacilityRepository;
import com.reservation.reservationservice.service.FacilityService;
import com.reservation.reservationservice.service.PropertyService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {
    private Util util = new Util();

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    PropertyService propertyService;

    @Autowired
    UserService userService;


    @Override
    public void createFacility(Facility facility) {
        User user = userService.getAuthUser();
        Optional<Facility> facility1= facilityRepository.findByName(facility.getName());
        if (facility1.isPresent()) {
            throw new ResourceAlreadyExistException("Facility already exist");
        }
        facility.setId(util.generateId());
        facility.setUser(user);
        facility.setCreatedBy(user.getEmail());
        facilityRepository.save(facility);
    }

    @Override
    public void editFacility(String facilityId, Facility facility) {
        Facility facility1 = getFacility(facilityId);
        facility1.setName(facility.getName());
        facility1.setChoice(facility.isChoice());
        facilityRepository.save(facility1);
    }

    @Override
    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }

    @Override
    public List<PropertyFacility> getAllFacilitiesByProperty(String propertyId) {
        Property property = propertyService.getProperty(propertyId);
        return property.getPropertyFacilities();
    }

    @Override
    public Facility getFacility(String facilityId) {
        Optional<Facility> facility = facilityRepository.findById(facilityId);
        facility.orElseThrow(() -> new ResourceNotFoundException("Facility not found with id -  " + facilityId));
        return facility.get();
    }

    @Override
    public void deleteFacility(String facilityId) {
        facilityRepository.deleteById(facilityId);
    }
}
