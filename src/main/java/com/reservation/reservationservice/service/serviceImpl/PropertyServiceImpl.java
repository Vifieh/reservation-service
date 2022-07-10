package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.repository.*;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PropertyServiceImpl implements PropertyService {
    private final Util util = new Util();

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    PropertyFacilityRepository propertyFacilityRepository;

    @Autowired
    BreakfastRepository breakfastRepository;

    @Autowired
    ExtraBedRepository extraBedRepository;

    @Autowired
    GuestExtraBedRepository guestExtraBedRepository;

    @Autowired
    PolicyRepository policyRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    UserService userService;

    @Autowired
    ContactDetailsService contactDetailsService;

    @Autowired
    PropertyTypeService propertyTypeService;

    @Autowired
    LanguageService languageService;

    @Autowired
    FacilityService facilityService;

    @Autowired
    BreakfastAvailableService breakfastAvailableService;

    @Autowired
    GuestService guestService;

    @Autowired
    PaymentOptionService paymentOptionService;

    @Autowired
    EmailService emailService;

    @Autowired
    CityService cityService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Property saveProperty(String propertyTypeId, PropertyPayload propertyPayload) {
        Property property = new Property();
        User user = userService.getAuthUser();
        Optional<Property> property1 = propertyRepository.findByName(property.getName());
        if (property1.isPresent()) {
            throw new ResourceAlreadyExistException("Property already exist");
        }
        property.setId(util.generateId());
        property.setUser(user);
        property.setCreatedBy(user.getEmail());
        property.setName(propertyPayload.getName());
        property.setRating(propertyPayload.getRating());
        property.setPropertyType(propertyTypeService.getPropertyType(propertyTypeId));
        PropertyAddress propertyAddress = contactDetailsService.addPropertyAddress(property, propertyPayload.getPropertyAddressPayload());
        property.setPropertyAddress(propertyAddress);
        property.setPropertyContactDetails(contactDetailsService
                .addPropertyContactDetails(property, propertyPayload.getPropertyContactDetailsPayload()));
        return propertyRepository.save(property);
    }

    @Override
    public void addFacilityServices(String propertyId, FacilitiesServicesPayload facilitiesServicesPayload) {
        Property property = getProperty(propertyId);
        Parking parking = this.modelMapper.map(facilitiesServicesPayload.getParkingPayload(), Parking.class);
        List<Language> languages = facilitiesServicesPayload.getLanguagePayload().stream().map(language ->
                modelMapper.map(language, Language.class)).collect(Collectors.toList());

        addParkingFacility(property, parking);
        addLanguage(property, languages);
        addFacilities(property, facilitiesServicesPayload.getPropertyFacilityPayloadList());
        addBreakfast(property, facilitiesServicesPayload.getBreakfastPayload());
    }

    @Override
    public void addPolicy(String propertyId, PolicyPayload policyPayload) {
        User user = userService.getAuthUser();
        Property property = getProperty(propertyId);
        Pet pet = new Pet();
        Policy policy = new Policy();
        policy.setId(util.generateId());
        policy.setUser(user);
        policy.setProperty(property);
        policy.setGuestCanCancel(policyPayload.getGuestCanCancel());
        policy.setGuestPay(policyPayload.getGuestPay());
        policy.setCheckInFrom(policyPayload.getCheckInFrom());
        policy.setCheckInTo(policyPayload.getCheckInTo());
        policy.setCheckOutFrom(policyPayload.getCheckOutFrom());
        policy.setCanAccommodateChildren(policyPayload.getCanAccommodateChildren());
        policyRepository.save(policy);
        pet.setId(util.generateId());
        pet.setProperty(property);
        pet.setAllowPets(policyPayload.getPetPayload().getAllowPets());
        pet.setCharge(policyPayload.getPetPayload().getCharge());
        petRepository.save(pet);
    }

    @Override
    public void addPaymentOptions(String propertyId, List<CustomPayload> paymentOptionsPayload) {
        Property property = getProperty(propertyId);
        for (CustomPayload paymentOption : paymentOptionsPayload) {
            PaymentOption paymentOption1 = paymentOptionService.getPaymentOptionByName(paymentOption.getName());
            property.getPaymentOptions().add(paymentOption1);
            propertyRepository.saveAndFlush(property);
        }
    }

    @Override
    public Property getProperty(String propertyId) {
        Optional<Property> property = propertyRepository.findById(propertyId);
        property.orElseThrow(() -> new ResourceNotFoundException("Property not found with id - " + propertyId));
        return property.get();
    }

    @Override
    public List<Property> getAllProperties(boolean pending, boolean completedRegistration) {
        return propertyRepository.findByPendingAndCompletedRegistrationOrderByRatingDesc(pending, completedRegistration);
    }

    @Override
    public List<Property> getAllPropertiesOfUSer() {
        User user = userService.getAuthUser();
        return user.getProperties();
    }

    @Override
    public void approveProperty(String propertyId) {
        Property property = getProperty(propertyId);
        Email approvedEmail = new Email();
        if (!property.isPending()) {
            throw new ResourceAlreadyExistException("Property is already approved");
        }
        property.setPending(false);
        emailService.approvedPropertyEmail(property.getUser(), approvedEmail, property);
        propertyRepository.save(property);
    }

    @Override
    public void completeRegistration(String propertyId) {
        User user = userService.getAuthUser();
        Email completedEmail = new Email();
        Property property = getProperty(propertyId);
        if (property.isCompletedRegistration()) {
            throw new ResourceAlreadyExistException("Property is already registered");
        }
        property.setCompletedRegistration(true);
        List<User> users = userService.getAllUsersByRole(ERole.ROLE_ADMIN);
        emailService.sendCompletedRegistration(user, completedEmail, property);
        emailService.sendCompletedRegistrationToAdmin(user, completedEmail, property, users.get(0).getEmail());
        propertyRepository.save(property);
    }

    @Override
    public Parking getParkingByProperty(String propertyId) {
        Property property = getProperty(propertyId);
        return property.getParking();
    }

    @Override
    public Policy getPolicyByProperty(String propertyId) {
        Property property = getProperty(propertyId);
        return property.getPolicy();
    }

    private void addParkingFacility(Property property, Parking parking) {
        User user = userService.getAuthUser();
        parking.setId(util.generateId());
        parking.setUser(user);
        parking.setCreatedBy(user.getEmail());
        parking.setProperty(property);
        parkingRepository.save(parking);
    }

    private void addLanguage(Property property, List<Language> languages) {
        for (Language language : languages) {
            Language language1 = languageService.getLanguageByName(language.getName());
            property.getLanguages().add(language1);
            propertyRepository.saveAndFlush(property);
        }
    }

    private void addFacilities(Property property, List<PropertyFacilityPayload> propertyFacilityPayloads) {
        for (PropertyFacilityPayload propertyFacilityPayload : propertyFacilityPayloads) {
            Facility facility = facilityService.getFacility(propertyFacilityPayload.getFacilityId());
            PropertyFacility propertyFacility = new PropertyFacility();
            propertyFacility.setId(new PropertyFacilityKey(property.getId(), facility.getId()));
            propertyFacility.setProperty(property);
            propertyFacility.setFacility(facility);
            propertyFacility.setStatus(propertyFacilityPayload.getStatus());
            propertyFacilityRepository.saveAndFlush(propertyFacility);
        }
    }

    private void addBreakfast(Property property, BreakfastPayload breakfastPayload) {
        User user = userService.getAuthUser();
        Breakfast breakfast = new Breakfast();
        breakfast.setId(util.generateId());
        breakfast.setUser(user);
        breakfast.setCreatedBy(user.getEmail());
        breakfast.setProperty(property);
        breakfast.setUnitPrice(breakfastPayload.getUnitPrice());
        breakfast.setAvailable(breakfastPayload.getAvailable());
        breakfast.setCurrency(breakfastPayload.getCurrency());
        breakfast.setBreakfastAvailableList(addBreakfastAvailable(breakfastPayload));
        breakfastRepository.save(breakfast);

    }

    private List<BreakfastAvailable> addBreakfastAvailable(BreakfastPayload breakfastPayload) {
        List<BreakfastAvailable> breakfastAvailableList = new ArrayList<>();
        for (DefaultPayload breakfastAvailablePayload : breakfastPayload.getBreakfastAvailablePayload()) {
            BreakfastAvailable breakfastAvailable1 =
                    breakfastAvailableService.getBreakfastAvailable(breakfastAvailablePayload.getId());
            breakfastAvailableList.add(breakfastAvailable1);
        }
        return breakfastAvailableList;
    }
}
