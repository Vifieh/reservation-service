package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Breakfast;
import com.reservation.reservationservice.model.BreakfastAvailable;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.BreakfastAvailableRepository;
import com.reservation.reservationservice.service.BreakfastAvailableService;
import com.reservation.reservationservice.service.PropertyService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreakfastAvailableServiceImpl implements BreakfastAvailableService {
    private Util util = new Util();

    @Autowired
    BreakfastAvailableRepository breakfastAvailableRepository;

    @Autowired
    UserService userService;

    @Autowired
    PropertyService propertyService;

    @Override
    public void createBreakfastAvailable(BreakfastAvailable breakfastAvailable) {
        User user = userService.getAuthUser();
        Optional<BreakfastAvailable> breakfastAvailable1= breakfastAvailableRepository.findByName(breakfastAvailable.getName());
        if (breakfastAvailable1.isPresent()) {
            throw new ResourceAlreadyExistException("Breakfast available already exist");
        }
        breakfastAvailable.setId(util.generateId());
        breakfastAvailable.setUser(user);
        breakfastAvailable.setCreatedBy(user.getEmail());
        breakfastAvailableRepository.save(breakfastAvailable);
    }

    @Override
    public void editBreakfastAvailable(String breakfastId, BreakfastAvailable breakfastAvailable) {
        BreakfastAvailable breakfastAvailable1 = getBreakfastAvailable(breakfastId);
        breakfastAvailable1.setName(breakfastAvailable.getName());
        breakfastAvailableRepository.save(breakfastAvailable1);
    }

    @Override
    public List<BreakfastAvailable> getAllBreakfastAvailable() {
        return breakfastAvailableRepository.findAll();
    }

    @Override
    public Breakfast getAllBreakfastByProperty(String propertyId) {
        Property property = propertyService.getProperty(propertyId);
        return property.getBreakfast();
    }

    @Override
    public BreakfastAvailable getBreakfastAvailable(String breakfastId) {
        Optional<BreakfastAvailable> breakfastAvailable = breakfastAvailableRepository.findById(breakfastId);
        breakfastAvailable.orElseThrow(() -> new ResourceNotFoundException("Breakfast available not found with id -  " + breakfastAvailable));
        return breakfastAvailable.get();
    }

    @Override
    public void deleteBreakfastAvailable(String breakfastId) {
        breakfastAvailableRepository.deleteById(breakfastId);
    }
}
