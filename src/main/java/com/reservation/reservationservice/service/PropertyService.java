package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Language;
import com.reservation.reservationservice.model.Parking;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyFacility;

import java.util.List;

public interface PropertyService {

    Property saveProperty(String propertyTypeId, Property property);

    Property getProperty(String propertyId);

    void addParkingFacility(String propertyId, Parking parking);

    void addLanguage(String propertyId, List<Language> languages);

    void addFacilities(String propertyId, PropertyFacility propertyFacility);
}
