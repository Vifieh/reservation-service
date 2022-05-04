package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.PropertyType;

import java.util.List;

public interface PropertyTypeService {

    void createPropertyType(PropertyType propertyType);

    void editPropertyType(String propertyTypeId, PropertyType propertyType);

    List<PropertyType> getAllPropertyTypes();

    PropertyType getPropertyType(String propertyTypeId);

    void deletePropertyType(String propertyTypeId);
}
