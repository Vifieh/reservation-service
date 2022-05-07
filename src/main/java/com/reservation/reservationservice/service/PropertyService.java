package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Property;

public interface PropertyService {

    Property saveProperty(String propertyTypeId, Property property);

    Property getProperty(String propertyId);
}
