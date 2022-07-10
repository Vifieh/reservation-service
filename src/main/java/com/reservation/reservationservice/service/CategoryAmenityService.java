package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.CategoryAmenity;

import java.util.List;

public interface CategoryAmenityService {

    void createCategoryAmenity(CategoryAmenity categoryAmenity);

    void editCategoryAmenity(String categoryId, CategoryAmenity categoryAmenity);

    List<CategoryAmenity> getAllCategoriesAmenities();

    CategoryAmenity getCategoryAmenity(String categoryId);

    void deleteCategoryAmenity(String categoryId);
}
