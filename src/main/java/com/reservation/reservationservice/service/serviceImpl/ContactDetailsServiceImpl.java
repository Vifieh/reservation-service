package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.model.UserContactDetails;
import com.reservation.reservationservice.repository.UserContactDetailsRepository;
import com.reservation.reservationservice.service.ContactDetailsService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {
    private final Util util = new Util();

    @Autowired
    UserService userService;

    @Autowired
    UserContactDetailsRepository contactDetailsRepository;

    @Override
    public void addContactDetails(UserContactDetails userContactDetails) {
        User user = userService.getAuthUser();
        userContactDetails.setId(util.generateId());
        userContactDetails.setUser(user);
        userContactDetails.setCreatedBy(user.getEmail());
        contactDetailsRepository.save(userContactDetails);
    }

    @Override
    public void editContactDetails(String contactId, UserContactDetails userContactDetails) {
        User user = userService.getAuthUser();
        UserContactDetails userContactDetails1 = getContactDetails(contactId);
        userContactDetails1.setLastName(userContactDetails.getLastName());
        userContactDetails1.setFirstName(userContactDetails.getFirstName());
        userContactDetails1.setPhoneNumber(userContactDetails.getPhoneNumber());
        userContactDetails1.setUser(user);
        contactDetailsRepository.save(userContactDetails1);
    }

    @Override
    public UserContactDetails getContactDetails(String contactId) {
        Optional<UserContactDetails> userContactDetails = contactDetailsRepository.findById(contactId);
        userContactDetails.orElseThrow(() ->
                new ResourceNotFoundException("Contact details not found with id - " + contactId));
        return userContactDetails.get();
    }


}
