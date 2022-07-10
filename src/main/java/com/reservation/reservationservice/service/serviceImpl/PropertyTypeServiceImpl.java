package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.PropertyType;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.PropertyTypeRepository;
import com.reservation.reservationservice.service.PropertyTypeService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyTypeServiceImpl implements PropertyTypeService {
    private final Util util = new Util();

    @Autowired
    UserService userService;

    @Autowired
    PropertyTypeRepository propertyTypeRepository;

    @Override
    public void createPropertyType(PropertyType propertyType) {
        User user = userService.getAuthUser();
        Optional<PropertyType> propertyType1 = propertyTypeRepository.findByName(propertyType.getName());
        if (propertyType1.isPresent()) {
            throw new ResourceAlreadyExistException("PropertyType already exist");
        }
        propertyType.setId(util.generateId());
        propertyType.setUser(user);
        propertyType.setCreatedBy(user.getEmail());
        propertyTypeRepository.save(propertyType);
    }

    @Override
    public void editPropertyType(String propertyTypeId, PropertyType propertyType) {
        User user = userService.getAuthUser();
        PropertyType propertyType1 = getPropertyType(propertyTypeId);
        propertyType1.setId(util.generateId());
        propertyType1.setName(propertyType.getName());
        propertyType1.setUser(user);
        propertyType1.setCreatedBy(user.getEmail());
        propertyTypeRepository.save(propertyType1);

    }

    @Override
    public List<PropertyType> getAllPropertyTypes() {
        return propertyTypeRepository.findAll();
    }

    @Override
    public PropertyType getPropertyType(String propertyTypeId) {
        Optional<PropertyType> propertyType = propertyTypeRepository.findById(propertyTypeId);
        propertyType.orElseThrow(() ->
                new ResourceNotFoundException("Property type not found with id - " + propertyTypeId));
        return propertyType.get();
    }

    @Override
    public void deletePropertyType(String propertyTypeId) {
        propertyTypeRepository.deleteById(propertyTypeId);
    }
}
