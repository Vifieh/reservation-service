package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Amenity;
import com.reservation.reservationservice.model.CategoryAmenity;

import java.util.List;

public interface AmenityService {

    void createAmenity(String categoryId, Amenity amenity);

    void editAmenity(String amenityId, Amenity amenity);

    List<Amenity> getAllAmenities();

    List<Amenity> getAmenitiesByCategory(String categoryId);

    Amenity getAmenity(String amenityId);

    void deleteAmenity(String amenityId);
}
