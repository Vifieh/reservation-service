package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Language;
import com.reservation.reservationservice.model.Parking;
import com.reservation.reservationservice.model.PaymentOption;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.payload.BreakfastPayload;
import com.reservation.reservationservice.payload.ExtraBedPayload;
import com.reservation.reservationservice.payload.PolicyPayload;
import com.reservation.reservationservice.payload.PropertyFacilityPayload;

import java.util.List;

public interface PropertyService {

    Property saveProperty(String propertyTypeId, Property property);

    Property getProperty(String propertyId);

    void addParkingFacility(String propertyId, Parking parking);

    void addLanguage(String propertyId, List<Language> languages);

    void addFacilities(String propertyId, List<PropertyFacilityPayload> propertyFacilityPayloads);

    void addBreakfast(String propertyId, BreakfastPayload breakfastPayload);

    void addExtraBedOption(String propertyId, ExtraBedPayload extraBedPayload);

    void addPolicy(String propertyId, PolicyPayload policyPayload);

    void adPaymentOption(String propertyId, List<PaymentOption> paymentOptions);
}
