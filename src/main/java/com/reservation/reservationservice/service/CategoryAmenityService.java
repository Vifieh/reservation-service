package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.CategoryAmenity;

public interface CategoryAmenityService {

    void createCategoryAmenity(CategoryAmenity categoryAmenity);

    void editCategoryAmenity(String categoryId, CategoryAmenity categoryAmenity);
}
