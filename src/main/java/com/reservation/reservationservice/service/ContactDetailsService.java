package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyAddress;
import com.reservation.reservationservice.model.PropertyContactDetails;
import com.reservation.reservationservice.model.UserContactDetails;

public interface ContactDetailsService {

    void addContactDetails(UserContactDetails userContactDetails);

    void editContactDetails(String contactId, UserContactDetails userContactDetails);

    UserContactDetails getContactDetails(String contactId);

    PropertyContactDetails addPropertyContactDetails(Property property, PropertyContactDetails propertyContactDetails);

    PropertyAddress addPropertyAddress(Property property, PropertyAddress propertyAddress);
}
