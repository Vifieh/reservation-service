package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.repository.*;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Property saveProperty(String propertyTypeId, Property property) {
        User user = userService.getAuthUser();
        Optional<Property> property1 = propertyRepository.findByName(property.getName());
        if (property1.isPresent()) {
            throw new ResourceAlreadyExistException("Property already exist");
        }
        property.setId(util.generateId());
        property.setUser(user);
        property.setCreatedBy(user.getEmail());
        property.setPropertyType(propertyTypeService.getPropertyType(propertyTypeId));
        property.setPropertyAddress(contactDetailsService.addPropertyAddress(property, property.getPropertyAddress()));
        property.setPropertyContactDetails(contactDetailsService
                .addPropertyContactDetails(property, property.getPropertyContactDetails()));
        return propertyRepository.save(property);
    }

    @Override
    public void addParkingFacility(String propertyId, Parking parking) {
        User user = userService.getAuthUser();
        Property property = getProperty(propertyId);
        parking.setId(util.generateId());
        parking.setUser(user);
        parking.setCreatedBy(user.getEmail());
        parking.setProperty(property);
        parkingRepository.save(parking);
    }

    @Override
    public void addLanguage(String propertyId, List<Language> languages) {
        Property property = getProperty(propertyId);
        for (Language language: languages) {
            Language language1 = languageService.getLanguageByName(language.getName());
            property.getLanguages().add(language1);
            propertyRepository.saveAndFlush(property);
        }
    }

    @Override
    public void addFacilities(String propertyId, List<PropertyFacilityPayload> propertyFacilityPayloads) {
        Property property = getProperty(propertyId);
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

    @Override
    public void addBreakfast(String propertyId, BreakfastPayload breakfastPayload) {
        User user = userService.getAuthUser();
        Property property = getProperty(propertyId);
        Breakfast breakfast = new Breakfast();
        breakfast.setId(util.generateId());
        breakfast.setUser(user);
        breakfast.setCreatedBy(user.getEmail());
        breakfast.setProperty(property);
        breakfast.setUnitPrice(breakfastPayload.getUnitPrice());
        breakfast.setAvailable(breakfastPayload.getAvailable());
        breakfast.setBreakfastAvailableList(addBreakfastAvailable(breakfastPayload));
        breakfastRepository.save(breakfast);

    }

    @Override
    public void addExtraBedOption(String propertyId, ExtraBedPayload extraBedPayload) {
        User user = userService.getAuthUser();
        Property property = getProperty(propertyId);
        ExtraBed extraBed = new ExtraBed();
        extraBed.setId(util.generateId());
        extraBed.setNumberOfExtraBeds(extraBedPayload.getNumberOfExtraBeds());
        extraBed.setUser(user);
        extraBed.setProperty(property);
        ExtraBed extraBed1 = extraBedRepository.save(extraBed);
        extraBedPayload.getGuestExtraBedPayloadList().forEach(guestExtraBedPayload -> {
            GuestExtraBed guestExtraBed = new GuestExtraBed();
            Guest guest = guestService.getGuest(guestExtraBedPayload.getGuestId());
            guestExtraBed.setId(new GuestExtraBedKey(guest.getId(), extraBed1.getId()));
            guestExtraBed.setExtraBed(extraBed1);
            guestExtraBed.setGuest(guest);
            guestExtraBed.setUnitPrice(guestExtraBedPayload.getUnitPrice());
            guestExtraBed.setRange(guestExtraBedPayload.getRange());
            guestExtraBedRepository.save(guestExtraBed);
        });
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
    public void adPaymentOption(String propertyId, List<PaymentOption> paymentOptions) {
        Property property = getProperty(propertyId);
        for (PaymentOption paymentOption: paymentOptions) {
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

    private List<BreakfastAvailable> addBreakfastAvailable(BreakfastPayload breakfastPayload) {
        List<BreakfastAvailable> breakfastAvailableList = new ArrayList<>();
        for (DefaultPayload breakfastAvailablePayload: breakfastPayload.getBreakfastAvailablePayload()) {
            BreakfastAvailable breakfastAvailable1 =
                    breakfastAvailableService.getBreakfastAvailable(breakfastAvailablePayload.getId());
            breakfastAvailableList.add(breakfastAvailable1);
        }
        return breakfastAvailableList;
    }


}
