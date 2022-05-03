package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.CategoryAmenity;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.CategoryAmenityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/")
@CrossOrigin
@RestController
public class CategoryAmenityController {

    @Autowired
    CategoryAmenityService categoryAmenityService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("protected/categoryAmenities")
    public ResponseEntity<ResponseMessage> createCategoryAmenity(@RequestBody @Valid CustomPayload categoryAmenityPayload) {
        CategoryAmenity categoryAmenity = convertCategoryToCategoryPayload(categoryAmenityPayload);
        categoryAmenityService.createCategoryAmenity(categoryAmenity);
        message = "CategoryAmenity created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("protected/categoryAmenities/{categoryId}")
    public ResponseEntity<ResponseMessage> editCategoryAmenity(@PathVariable("categoryId") String categoryId,
                                                               @RequestBody @Valid CustomPayload categoryAmenityPayload) {
        CategoryAmenity categoryAmenity = convertCategoryToCategoryPayload(categoryAmenityPayload);
        categoryAmenityService.editCategoryAmenity(categoryId, categoryAmenity);
        message = "CategoryAmenity edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("public/categoryAmenities")
    public ResponseEntity<List<CustomDto>> getCategoriesAmenities() {
        List<CategoryAmenity> categoryAmenities = categoryAmenityService.getAllCategoriesAmenities();
        List<CustomDto> categoryAmenitiesDto = categoryAmenities.stream()
                .map(categoryAmenity -> this.modelMapper.map(categoryAmenity, CustomDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoryAmenitiesDto, HttpStatus.OK);
    }

    @GetMapping("public/categoryAmenities/{categoryId}")
    public ResponseEntity<CustomDto> getCategoryAmenity(@PathVariable("categoryId") String categoryId) {
        CategoryAmenity categoryAmenity = categoryAmenityService.getCategoryAmenity(categoryId);
        CustomDto categoryAmenityDto = this.modelMapper.map(categoryAmenity, CustomDto.class);
        return new ResponseEntity<>(categoryAmenityDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("protected/categoryAmenities/{categoryId}")
    public ResponseEntity<ResponseMessage> deleteCategoryAmenity(@PathVariable("categoryId") String categoryId) {
        categoryAmenityService.deleteCategoryAmenity(categoryId);
        message = "CategoryAmenity deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    private CategoryAmenity convertCategoryToCategoryPayload(CustomPayload categoryAmenityPayload) {
        return this.modelMapper.map(categoryAmenityPayload, CategoryAmenity.class);
    }
}
