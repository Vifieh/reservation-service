package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Guest;

import java.util.List;

public interface GuestService {

    void createGuest(Guest guest);

    void editGuest(String guestId, Guest guest);

    List<Guest> getAllGuests();

    Guest getGuest(String guestId);

    void deleteGuest(String guestId);

}
