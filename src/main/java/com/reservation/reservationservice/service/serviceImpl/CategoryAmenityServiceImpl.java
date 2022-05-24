package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.CategoryAmenity;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.CategoryAmenityRepository;
import com.reservation.reservationservice.service.CategoryAmenityService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryAmenityServiceImpl implements CategoryAmenityService {
    private final Util util = new Util();

    @Autowired
    UserService userService;

    @Autowired
    CategoryAmenityRepository categoryAmenityRepository;


    @Override
    public void createCategoryAmenity(CategoryAmenity categoryAmenity) {
        User user = userService.getAuthUser();
        Optional<CategoryAmenity> categoryAmenity1 = categoryAmenityRepository.findByName(categoryAmenity.getName());
        if (categoryAmenity1.isPresent()) {
            throw new ResourceAlreadyExistException("Category already exist");
        }
        categoryAmenity.setId(util.generateId());
        categoryAmenity.setUser(user);
        categoryAmenity.setCreatedBy(user.getEmail());
        categoryAmenityRepository.save(categoryAmenity);
    }

    @Override
    public void editCategoryAmenity(String categoryId, CategoryAmenity categoryAmenity) {
        User user = userService.getAuthUser();
        CategoryAmenity categoryAmenity1 = getCategoryAmenity(categoryId);
        categoryAmenity1.setName(categoryAmenity.getName());
        categoryAmenity1.setUser(user);
        categoryAmenity1.setCreatedBy(user.getEmail());
        categoryAmenityRepository.save(categoryAmenity1);
    }

    @Override
    public List<CategoryAmenity> getAllCategoriesAmenities() {
        return categoryAmenityRepository.findAll();
    }

    @Override
    public CategoryAmenity getCategoryAmenity(String categoryId) {
        Optional<CategoryAmenity> categoryAmenity = categoryAmenityRepository.findById(categoryId);
        categoryAmenity.orElseThrow(() ->
                new ResourceNotFoundException("Category Amenity not found with id - " + categoryId));
        return categoryAmenity.get();
    }

    @Override
    public void deleteCategoryAmenity(String categoryId) {
        categoryAmenityRepository.deleteById(categoryId);
    }
}
