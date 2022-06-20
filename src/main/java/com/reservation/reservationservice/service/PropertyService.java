package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.payload.*;

import java.util.List;

public interface PropertyService {

    Property saveProperty(String propertyTypeId, PropertyPayload propertyPayload);

    Property getProperty(String propertyId);

    List<Property> getAllProperties(boolean pending);

    List<Property> getAllPropertiesOfUSer();

    void addFacilityServices(String propertyId, FacilitiesServicesPayload facilitiesServicesPayload);

    void addPolicy(String propertyId, PolicyPayload policyPayload);

    void addPaymentOptions(String propertyId, List<CustomPayload> paymentOptionsPayload);

    void approveProperty(String propertyId);
}
