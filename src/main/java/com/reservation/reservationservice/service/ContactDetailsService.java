package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyAddress;
import com.reservation.reservationservice.model.PropertyContactDetails;
import com.reservation.reservationservice.model.UserContactDetails;
import com.reservation.reservationservice.payload.PropertyAddressPayload;
import com.reservation.reservationservice.payload.PropertyContactDetailsPayload;

public interface ContactDetailsService {

    void addContactDetails(UserContactDetails userContactDetails);

    void editContactDetails(String contactId, UserContactDetails userContactDetails);

    UserContactDetails getContactDetails(String contactId);

    PropertyContactDetails addPropertyContactDetails(Property property, PropertyContactDetailsPayload contactDetailsPayload);

    PropertyAddress addPropertyAddress(Property property, PropertyAddressPayload addressPayload);
}
