package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Guest;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.GuestRepository;
import com.reservation.reservationservice.service.GuestService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {
    private final Util util = new Util();

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    UserService userService;

    @Override
    public void createGuest(Guest guest) {
        User user = userService.getAuthUser();
        Optional<Guest> guest1 = guestRepository.findByName(guest.getName());
        if(guest1.isPresent()) {
            throw new ResourceAlreadyExistException("Guest already exist");
        }
        guest.setId(util.generateId());
        guest.setUser(user);
        guest.setCreatedBy(user.getEmail());
        guestRepository.save(guest);
    }

    @Override
    public void editGuest(String guestId, Guest guest) {
        User user = userService.getAuthUser();
        Guest guest1 = getGuest(guestId);
            guest1.setName(guest.getName());
            guest1.setUser(user);
            guest1.setCreatedBy(user.getEmail());
            guestRepository.save(guest1);
    }

    @Override
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest getGuest(String guestId) {
        Optional<Guest> guest = guestRepository.findById(guestId);
        guest.orElseThrow(() -> new ResourceNotFoundException("Guest not found with id - " + guestId));
        return guest.get();
    }

    @Override
    public void deleteGuest(String guestId) {
        guestRepository.deleteById(guestId);
    }
}
