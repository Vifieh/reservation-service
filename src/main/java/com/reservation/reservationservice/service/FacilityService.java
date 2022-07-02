package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Facility;
import com.reservation.reservationservice.model.PropertyFacility;

import java.util.List;

public interface FacilityService {

    void createFacility(Facility Facility);

    void editFacility(String facilityId, Facility Facility);

    List<Facility> getAllFacilities();

    List<PropertyFacility> getAllFacilitiesByProperty(String propertyId);

    Facility getFacility(String facilityId);

    void deleteFacility(String facilityId);
}
