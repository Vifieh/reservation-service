package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.UserContactDetails;

public interface ContactDetailsService {

    void addContactDetails(UserContactDetails userContactDetails);

    void editContactDetails(String contactId, UserContactDetails userContactDetails);

    UserContactDetails getContactDetails(String contactId);

}
