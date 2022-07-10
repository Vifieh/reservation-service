package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Amenity;
import com.reservation.reservationservice.model.CategoryAmenity;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.AmenityRepository;
import com.reservation.reservationservice.service.AmenityService;
import com.reservation.reservationservice.service.CategoryAmenityService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AmenityServiceImpl implements AmenityService {
    private final Util util = new Util();

    @Autowired
    UserService userService;

    @Autowired
    CategoryAmenityService categoryAmenityService;

    @Autowired
    AmenityRepository amenityRepository;


    @Override
    public void createAmenity(String categoryId, Amenity amenity) {
        User user = userService.getAuthUser();
        CategoryAmenity categoryAmenity = categoryAmenityService.getCategoryAmenity(categoryId);
        Optional<Amenity> amenity1 = amenityRepository.findByName(amenity.getName());
        if (amenity1.isPresent()) {
            throw new ResourceAlreadyExistException("Amenity already exist");
        }
        amenity.setId(util.generateId());
        amenity.setUser(user);
        amenity.setCreatedBy(user.getEmail());
        amenity.setCategoryAmenity(categoryAmenity);
        amenityRepository.save(amenity);
    }

    @Override
    public void editAmenity(String amenityId, Amenity amenity) {
        User user = userService.getAuthUser();
        Amenity amenity1 = getAmenity(amenityId);
        amenity1.setName(amenity.getName());
        amenity1.setMostRequested(amenity.isMostRequested());
        amenity1.setUser(user);
        amenity1.setCreatedBy(user.getEmail());
        amenityRepository.save(amenity1);
    }

    @Override
    public List<Amenity> getAllAmenities(boolean mostRequested) {
        return amenityRepository.findAllByMostRequested(mostRequested);
    }

    @Override
    public List<Amenity> getAmenitiesByCategory(String categoryId, boolean mostRequested) {
       CategoryAmenity categoryAmenity = categoryAmenityService.getCategoryAmenity(categoryId);
        return amenityRepository.findByAndCategoryAmenityAndMostRequested(categoryAmenity, mostRequested);
    }


    @Override
    public Amenity getAmenity(String amenityId) {
        Optional<Amenity> amenity = amenityRepository.findById(amenityId);
        amenity.orElseThrow(() ->
                new ResourceNotFoundException("CategoryAmenity not found with id - " + amenityId));
        return amenity.get();
    }

    @Override
    public void deleteAmenity(String amenityId) {
        amenityRepository.deleteById(amenityId);
    }
}
