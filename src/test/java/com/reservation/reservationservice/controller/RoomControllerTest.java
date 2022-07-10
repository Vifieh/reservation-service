package com.reservation.reservationservice.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.reservationservice.constants.Available;
import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.constants.Default;
import com.reservation.reservationservice.constants.Policy;
import com.reservation.reservationservice.constants.Reservation;
import com.reservation.reservationservice.constants.Site;
import com.reservation.reservationservice.constants.Size;
import com.reservation.reservationservice.constants.Type;
import com.reservation.reservationservice.model.Amenity;
import com.reservation.reservationservice.model.Breakfast;
import com.reservation.reservationservice.model.BreakfastAvailable;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.ConfirmationToken;
import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.model.ExtraBed;
import com.reservation.reservationservice.model.GuestExtraBed;
import com.reservation.reservationservice.model.Language;
import com.reservation.reservationservice.model.Parking;
import com.reservation.reservationservice.model.PaymentOption;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyAddress;
import com.reservation.reservationservice.model.PropertyContactDetails;
import com.reservation.reservationservice.model.PropertyFacility;
import com.reservation.reservationservice.model.PropertyType;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.model.RoomBedAvailable;
import com.reservation.reservationservice.model.RoomName;
import com.reservation.reservationservice.model.RoomReservation;
import com.reservation.reservationservice.model.RoomReservationItem;
import com.reservation.reservationservice.model.RoomType;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.model.UserContactDetails;
import com.reservation.reservationservice.payload.ExtraBedOptionsAndRoomAmenitiesPayload;
import com.reservation.reservationservice.payload.ExtraBedPayload;
import com.reservation.reservationservice.payload.RoomAmenityPayload;
import com.reservation.reservationservice.payload.RoomBedAvailablePayload;
import com.reservation.reservationservice.payload.RoomPayload;
import com.reservation.reservationservice.service.RoomService;

import java.time.LocalDateTime;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RoomController.class})
@ExtendWith(SpringExtension.class)
public class RoomControllerTest {
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private RoomController roomController;

    @MockBean
    private RoomService roomService;

    @Test
    public void testCreateRoom() throws Exception {
        doNothing().when(this.roomService).createRoom(anyString(), (RoomPayload) any());

        RoomPayload roomPayload = new RoomPayload();
        roomPayload.setNumberOfRooms(10);
        roomPayload.setSize(Size.SQUARE_METRES);
        roomPayload.setNumberOfGuests(10);
        roomPayload.setRoomBedAvailablePayloadList(new ArrayList<RoomBedAvailablePayload>());
        roomPayload.setName("Name");
        roomPayload.setSmokingPolicy(Policy.NO);
        roomPayload.setRoomSize(10.0);
        roomPayload.setRoomNameId("42");
        roomPayload.setCurrency(Currency.XAF);
        roomPayload.setUnitPrice(10.0);
        String content = (new ObjectMapper()).writeValueAsString(roomPayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/protected/rooms")
                .param("propertyId", "foo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Room created successfully!\"}"));
    }

    @Test
    public void testAddExtraBedOptionsAndRoomAmenities() throws Exception {
        doNothing().when(this.roomService)
                .addExtraBedOptionsAndRoomAmenities(anyString(), (ExtraBedOptionsAndRoomAmenitiesPayload) any());

        ExtraBedOptionsAndRoomAmenitiesPayload extraBedOptionsAndRoomAmenitiesPayload = new ExtraBedOptionsAndRoomAmenitiesPayload();
        extraBedOptionsAndRoomAmenitiesPayload.setExtraBedPayload(new ExtraBedPayload());
        extraBedOptionsAndRoomAmenitiesPayload.setRoomAmenityPayloadList(new ArrayList<RoomAmenityPayload>());
        String content = (new ObjectMapper()).writeValueAsString(extraBedOptionsAndRoomAmenitiesPayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/rooms/properties/{propertyId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Amenities added successfully\"}"));
    }

    @Test
    public void testGetAllRoomsOfUserByProperty() throws Exception {
        when(this.roomService.getAllRoomsOfUserByProperty(anyString())).thenReturn(new ArrayList<Room>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/protected/rooms")
                .param("propertyId", "foo");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllRoomsOfUserByProperty2() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUserContactDetails(new UserContactDetails());
        user.setTokens(new ArrayList<ConfirmationToken>());
        user.setCountries(new ArrayList<Country>());
        user.setPropertyTypes(new ArrayList<PropertyType>());
        user.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setProperties(new ArrayList<Property>());
        user.setEnabled(true);
        user.setRole(new ArrayList<Role>());
        user.setRooms(new ArrayList<Room>());
        user.setId("42");
        user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setParkings(new ArrayList<Parking>());
        user.setCities(new ArrayList<City>());

        PropertyType propertyType = new PropertyType();
        propertyType.setUser(user);
        propertyType.setId("42");
        propertyType.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyType.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyType.setName("?");
        propertyType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        propertyType.setProperties(new ArrayList<Property>());
        propertyType.setDescription("The characteristics of someone or something");

        UserContactDetails userContactDetails = new UserContactDetails();
        userContactDetails.setLastName("Doe");
        userContactDetails.setUser(new User());
        userContactDetails.setId("42");
        userContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails.setPhoneNumber("4105551212");
        userContactDetails.setFirstName("Jane");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setUserContactDetails(userContactDetails);
        user1.setTokens(new ArrayList<ConfirmationToken>());
        user1.setCountries(new ArrayList<Country>());
        user1.setPropertyTypes(new ArrayList<PropertyType>());
        user1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setProperties(new ArrayList<Property>());
        user1.setEnabled(true);
        user1.setRole(new ArrayList<Role>());
        user1.setRooms(new ArrayList<Room>());
        user1.setId("42");
        user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user1.setParkings(new ArrayList<Parking>());
        user1.setCities(new ArrayList<City>());

        Property property = new Property();
        property.setRating(0);
        property.setRoomReservationList(new ArrayList<RoomReservation>());
        property.setPropertyType(new PropertyType());
        property.setUser(new User());
        property.setParking(new Parking());
        property.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property.setCompletedRegistration(true);
        property.setName("?");
        property.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property.setPending(true);
        property.setPaymentOptions(new ArrayList<PaymentOption>());
        property.setRooms(new ArrayList<Room>());
        property.setId("42");
        property.setLanguages(new ArrayList<Language>());
        property.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property.setPropertyAddress(new PropertyAddress());
        property.setPropertyContactDetails(new PropertyContactDetails());
        property.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property.setBreakfast(new Breakfast());
        property.setExtraBed(new ExtraBed());
        property.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setUserContactDetails(new UserContactDetails());
        user2.setTokens(new ArrayList<ConfirmationToken>());
        user2.setCountries(new ArrayList<Country>());
        user2.setPropertyTypes(new ArrayList<PropertyType>());
        user2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setProperties(new ArrayList<Property>());
        user2.setEnabled(true);
        user2.setRole(new ArrayList<Role>());
        user2.setRooms(new ArrayList<Room>());
        user2.setId("42");
        user2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setParkings(new ArrayList<Parking>());
        user2.setCities(new ArrayList<City>());

        Parking parking = new Parking();
        parking.setType(Type.PRIVATE);
        parking.setProperty(property);
        parking.setUser(user2);
        parking.setId("42");
        parking.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        parking.setAvailable(Available.NO);
        parking.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        parking.setReservation(Reservation.RESERVATION);
        parking.setSite(Site.ON_SITE);
        parking.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        parking.setCurrency(Currency.XAF);
        parking.setUnitPrice(10.0);

        Property property1 = new Property();
        property1.setRating(0);
        property1.setRoomReservationList(new ArrayList<RoomReservation>());
        property1.setPropertyType(new PropertyType());
        property1.setUser(new User());
        property1.setParking(new Parking());
        property1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property1.setCompletedRegistration(true);
        property1.setName("?");
        property1.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property1.setPending(true);
        property1.setPaymentOptions(new ArrayList<PaymentOption>());
        property1.setRooms(new ArrayList<Room>());
        property1.setId("42");
        property1.setLanguages(new ArrayList<Language>());
        property1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property1.setPropertyAddress(new PropertyAddress());
        property1.setPropertyContactDetails(new PropertyContactDetails());
        property1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property1.setBreakfast(new Breakfast());
        property1.setExtraBed(new ExtraBed());
        property1.setPolicy(new com.reservation.reservationservice.model.Policy());

        City city = new City();
        city.setUser(new User());
        city.setId("42");
        city.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        city.setPropertyAddresses(new ArrayList<PropertyAddress>());
        city.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        city.setName("?");
        city.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        city.setCountry(new Country());

        PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setProperty(property1);
        propertyAddress.setId("42");
        propertyAddress.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyAddress.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyAddress.setCode("?");
        propertyAddress.setCity(city);
        propertyAddress.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        propertyAddress.setAddressLine2("42 Main St");
        propertyAddress.setStreetAddress("42 Main St");

        Property property2 = new Property();
        property2.setRating(0);
        property2.setRoomReservationList(new ArrayList<RoomReservation>());
        property2.setPropertyType(new PropertyType());
        property2.setUser(new User());
        property2.setParking(new Parking());
        property2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property2.setCompletedRegistration(true);
        property2.setName("?");
        property2.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property2.setPending(true);
        property2.setPaymentOptions(new ArrayList<PaymentOption>());
        property2.setRooms(new ArrayList<Room>());
        property2.setId("42");
        property2.setLanguages(new ArrayList<Language>());
        property2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property2.setPropertyAddress(new PropertyAddress());
        property2.setPropertyContactDetails(new PropertyContactDetails());
        property2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property2.setBreakfast(new Breakfast());
        property2.setExtraBed(new ExtraBed());
        property2.setPolicy(new com.reservation.reservationservice.model.Policy());

        PropertyContactDetails propertyContactDetails = new PropertyContactDetails();
        propertyContactDetails.setProperty(property2);
        propertyContactDetails.setId("42");
        propertyContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyContactDetails.setName("?");
        propertyContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        propertyContactDetails.setPhoneNumber("4105551212");
        propertyContactDetails.setCompanyName("?");
        propertyContactDetails.setAlternativeNumber("?");

        Property property3 = new Property();
        property3.setRating(0);
        property3.setRoomReservationList(new ArrayList<RoomReservation>());
        property3.setPropertyType(new PropertyType());
        property3.setUser(new User());
        property3.setParking(new Parking());
        property3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property3.setCompletedRegistration(true);
        property3.setName("?");
        property3.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property3.setPending(true);
        property3.setPaymentOptions(new ArrayList<PaymentOption>());
        property3.setRooms(new ArrayList<Room>());
        property3.setId("42");
        property3.setLanguages(new ArrayList<Language>());
        property3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property3.setPropertyAddress(new PropertyAddress());
        property3.setPropertyContactDetails(new PropertyContactDetails());
        property3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property3.setBreakfast(new Breakfast());
        property3.setExtraBed(new ExtraBed());
        property3.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setPassword("iloveyou");
        user3.setUserContactDetails(new UserContactDetails());
        user3.setTokens(new ArrayList<ConfirmationToken>());
        user3.setCountries(new ArrayList<Country>());
        user3.setPropertyTypes(new ArrayList<PropertyType>());
        user3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user3.setProperties(new ArrayList<Property>());
        user3.setEnabled(true);
        user3.setRole(new ArrayList<Role>());
        user3.setRooms(new ArrayList<Room>());
        user3.setId("42");
        user3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user3.setParkings(new ArrayList<Parking>());
        user3.setCities(new ArrayList<City>());

        Breakfast breakfast = new Breakfast();
        breakfast.setProperty(property3);
        breakfast.setUser(user3);
        breakfast.setId("42");
        breakfast.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        breakfast.setAvailable(Available.NO);
        breakfast.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        breakfast.setBreakfastAvailableList(new ArrayList<BreakfastAvailable>());
        breakfast.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        breakfast.setCurrency(Currency.XAF);
        breakfast.setUnitPrice(10.0);

        Property property4 = new Property();
        property4.setRating(0);
        property4.setRoomReservationList(new ArrayList<RoomReservation>());
        property4.setPropertyType(new PropertyType());
        property4.setUser(new User());
        property4.setParking(new Parking());
        property4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property4.setCompletedRegistration(true);
        property4.setName("?");
        property4.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property4.setPending(true);
        property4.setPaymentOptions(new ArrayList<PaymentOption>());
        property4.setRooms(new ArrayList<Room>());
        property4.setId("42");
        property4.setLanguages(new ArrayList<Language>());
        property4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property4.setPropertyAddress(new PropertyAddress());
        property4.setPropertyContactDetails(new PropertyContactDetails());
        property4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property4.setBreakfast(new Breakfast());
        property4.setExtraBed(new ExtraBed());
        property4.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setPassword("iloveyou");
        user4.setUserContactDetails(new UserContactDetails());
        user4.setTokens(new ArrayList<ConfirmationToken>());
        user4.setCountries(new ArrayList<Country>());
        user4.setPropertyTypes(new ArrayList<PropertyType>());
        user4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user4.setProperties(new ArrayList<Property>());
        user4.setEnabled(true);
        user4.setRole(new ArrayList<Role>());
        user4.setRooms(new ArrayList<Room>());
        user4.setId("42");
        user4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user4.setParkings(new ArrayList<Parking>());
        user4.setCities(new ArrayList<City>());

        ExtraBed extraBed = new ExtraBed();
        extraBed.setProperty(property4);
        extraBed.setUser(user4);
        extraBed.setGuestExtraBeds(new ArrayList<GuestExtraBed>());
        extraBed.setId("42");
        extraBed.setNumberOfExtraBeds(10);

        Property property5 = new Property();
        property5.setRating(0);
        property5.setRoomReservationList(new ArrayList<RoomReservation>());
        property5.setPropertyType(new PropertyType());
        property5.setUser(new User());
        property5.setParking(new Parking());
        property5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property5.setCompletedRegistration(true);
        property5.setName("?");
        property5.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property5.setPending(true);
        property5.setPaymentOptions(new ArrayList<PaymentOption>());
        property5.setRooms(new ArrayList<Room>());
        property5.setId("42");
        property5.setLanguages(new ArrayList<Language>());
        property5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property5.setPropertyAddress(new PropertyAddress());
        property5.setPropertyContactDetails(new PropertyContactDetails());
        property5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property5.setBreakfast(new Breakfast());
        property5.setExtraBed(new ExtraBed());
        property5.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setPassword("iloveyou");
        user5.setUserContactDetails(new UserContactDetails());
        user5.setTokens(new ArrayList<ConfirmationToken>());
        user5.setCountries(new ArrayList<Country>());
        user5.setPropertyTypes(new ArrayList<PropertyType>());
        user5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user5.setProperties(new ArrayList<Property>());
        user5.setEnabled(true);
        user5.setRole(new ArrayList<Role>());
        user5.setRooms(new ArrayList<Room>());
        user5.setId("42");
        user5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user5.setParkings(new ArrayList<Parking>());
        user5.setCities(new ArrayList<City>());

        com.reservation.reservationservice.model.Policy policy = new com.reservation.reservationservice.model.Policy();
        policy.setCheckInTo("alice.liddell@example.org");
        policy.setGuestCanCancel("?");
        policy.setProperty(property5);
        policy.setUser(user5);
        policy.setId("42");
        policy.setCheckOutTo("alice.liddell@example.org");
        policy.setCanAccommodateChildren(Default.NO);
        policy.setCheckOutFrom("jane.doe@example.org");
        policy.setGuestPay("?");
        policy.setCheckInFrom("jane.doe@example.org");

        Property property6 = new Property();
        property6.setRating(0);
        property6.setRoomReservationList(new ArrayList<RoomReservation>());
        property6.setPropertyType(propertyType);
        property6.setUser(user1);
        property6.setParking(parking);
        property6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property6.setCompletedRegistration(true);
        property6.setName("?");
        property6.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property6.setPending(true);
        property6.setPaymentOptions(new ArrayList<PaymentOption>());
        property6.setRooms(new ArrayList<Room>());
        property6.setId("42");
        property6.setLanguages(new ArrayList<Language>());
        property6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property6.setPropertyAddress(propertyAddress);
        property6.setPropertyContactDetails(propertyContactDetails);
        property6.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property6.setBreakfast(breakfast);
        property6.setExtraBed(extraBed);
        property6.setPolicy(policy);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setPassword("iloveyou");
        user6.setUserContactDetails(new UserContactDetails());
        user6.setTokens(new ArrayList<ConfirmationToken>());
        user6.setCountries(new ArrayList<Country>());
        user6.setPropertyTypes(new ArrayList<PropertyType>());
        user6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user6.setProperties(new ArrayList<Property>());
        user6.setEnabled(true);
        user6.setRole(new ArrayList<Role>());
        user6.setRooms(new ArrayList<Room>());
        user6.setId("42");
        user6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user6.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user6.setParkings(new ArrayList<Parking>());
        user6.setCities(new ArrayList<City>());

        UserContactDetails userContactDetails1 = new UserContactDetails();
        userContactDetails1.setLastName("Doe");
        userContactDetails1.setUser(user6);
        userContactDetails1.setId("42");
        userContactDetails1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails1.setPhoneNumber("4105551212");
        userContactDetails1.setFirstName("Jane");

        User user7 = new User();
        user7.setEmail("jane.doe@example.org");
        user7.setPassword("iloveyou");
        user7.setUserContactDetails(userContactDetails1);
        user7.setTokens(new ArrayList<ConfirmationToken>());
        user7.setCountries(new ArrayList<Country>());
        user7.setPropertyTypes(new ArrayList<PropertyType>());
        user7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user7.setProperties(new ArrayList<Property>());
        user7.setEnabled(true);
        user7.setRole(new ArrayList<Role>());
        user7.setRooms(new ArrayList<Room>());
        user7.setId("42");
        user7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user7.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user7.setParkings(new ArrayList<Parking>());
        user7.setCities(new ArrayList<City>());

        UserContactDetails userContactDetails2 = new UserContactDetails();
        userContactDetails2.setLastName("Doe");
        userContactDetails2.setUser(new User());
        userContactDetails2.setId("42");
        userContactDetails2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails2.setPhoneNumber("4105551212");
        userContactDetails2.setFirstName("Jane");

        User user8 = new User();
        user8.setEmail("jane.doe@example.org");
        user8.setPassword("iloveyou");
        user8.setUserContactDetails(userContactDetails2);
        user8.setTokens(new ArrayList<ConfirmationToken>());
        user8.setCountries(new ArrayList<Country>());
        user8.setPropertyTypes(new ArrayList<PropertyType>());
        user8.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user8.setProperties(new ArrayList<Property>());
        user8.setEnabled(true);
        user8.setRole(new ArrayList<Role>());
        user8.setRooms(new ArrayList<Room>());
        user8.setId("42");
        user8.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user8.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user8.setParkings(new ArrayList<Parking>());
        user8.setCities(new ArrayList<City>());

        User user9 = new User();
        user9.setEmail("jane.doe@example.org");
        user9.setPassword("iloveyou");
        user9.setUserContactDetails(new UserContactDetails());
        user9.setTokens(new ArrayList<ConfirmationToken>());
        user9.setCountries(new ArrayList<Country>());
        user9.setPropertyTypes(new ArrayList<PropertyType>());
        user9.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user9.setProperties(new ArrayList<Property>());
        user9.setEnabled(true);
        user9.setRole(new ArrayList<Role>());
        user9.setRooms(new ArrayList<Room>());
        user9.setId("42");
        user9.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user9.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user9.setParkings(new ArrayList<Parking>());
        user9.setCities(new ArrayList<City>());

        RoomType roomType = new RoomType();
        roomType.setRoomNames(new ArrayList<RoomName>());
        roomType.setUser(user9);
        roomType.setId("42");
        roomType.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomType.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomType.setName("?");
        roomType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");

        Property property7 = new Property();
        property7.setRating(0);
        property7.setRoomReservationList(new ArrayList<RoomReservation>());
        property7.setPropertyType(new PropertyType());
        property7.setUser(new User());
        property7.setParking(new Parking());
        property7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property7.setCompletedRegistration(true);
        property7.setName("?");
        property7.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property7.setPending(true);
        property7.setPaymentOptions(new ArrayList<PaymentOption>());
        property7.setRooms(new ArrayList<Room>());
        property7.setId("42");
        property7.setLanguages(new ArrayList<Language>());
        property7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property7.setPropertyAddress(new PropertyAddress());
        property7.setPropertyContactDetails(new PropertyContactDetails());
        property7.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property7.setBreakfast(new Breakfast());
        property7.setExtraBed(new ExtraBed());
        property7.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user10 = new User();
        user10.setEmail("jane.doe@example.org");
        user10.setPassword("iloveyou");
        user10.setUserContactDetails(new UserContactDetails());
        user10.setTokens(new ArrayList<ConfirmationToken>());
        user10.setCountries(new ArrayList<Country>());
        user10.setPropertyTypes(new ArrayList<PropertyType>());
        user10.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user10.setProperties(new ArrayList<Property>());
        user10.setEnabled(true);
        user10.setRole(new ArrayList<Role>());
        user10.setRooms(new ArrayList<Room>());
        user10.setId("42");
        user10.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user10.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user10.setParkings(new ArrayList<Parking>());
        user10.setCities(new ArrayList<City>());

        RoomName roomName = new RoomName();
        roomName.setUser(new User());
        roomName.setId("42");
        roomName.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName.setName("?");
        roomName.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        roomName.setRoomType(new RoomType());
        roomName.setRoom(new Room());

        Room room = new Room();
        room.setProperty(property7);
        room.setUser(user10);
        room.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room.setName("?");
        room.setSmokingPolicy(com.reservation.reservationservice.constants.Policy.NO);
        room.setRoomSize(10.0);
        room.setRoomName(roomName);
        room.setAmenities(new ArrayList<Amenity>());
        room.setNumberOfRooms(10);
        room.setSize(Size.SQUARE_METRES);
        room.setId("42");
        room.setNumberOfGuests(10);
        room.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        room.setCurrency(Currency.XAF);
        room.setRoomBedAvailables(new ArrayList<RoomBedAvailable>());
        room.setUnitPrice(10.0);
        room.setRoomReservationItemList(new ArrayList<RoomReservationItem>());

        RoomName roomName1 = new RoomName();
        roomName1.setUser(user8);
        roomName1.setId("42");
        roomName1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName1.setName("?");
        roomName1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        roomName1.setRoomType(roomType);
        roomName1.setRoom(room);

        Room room1 = new Room();
        room1.setProperty(property6);
        room1.setUser(user7);
        room1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room1.setName("?");
        room1.setSmokingPolicy(com.reservation.reservationservice.constants.Policy.NO);
        room1.setRoomSize(10.0);
        room1.setRoomName(roomName1);
        room1.setAmenities(new ArrayList<Amenity>());
        room1.setNumberOfRooms(10);
        room1.setSize(Size.SQUARE_METRES);
        room1.setId("42");
        room1.setNumberOfGuests(10);
        room1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        room1.setCurrency(Currency.XAF);
        room1.setRoomBedAvailables(new ArrayList<RoomBedAvailable>());
        room1.setUnitPrice(10.0);
        room1.setRoomReservationItemList(new ArrayList<RoomReservationItem>());

        ArrayList<Room> roomList = new ArrayList<Room>();
        roomList.add(room1);
        when(this.roomService.getAllRoomsOfUserByProperty(anyString())).thenReturn(roomList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/protected/rooms")
                .param("propertyId", "foo");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":\"42\",\"name\":\"?\",\"numberOfRooms\":10,\"roomSize\":10.0,\"unitPrice\":10.0,\"numberOfGuests\":10,"
                                        + "\"roomName\":\"?\",\"size\":\"SQUARE_METRES\",\"smokingPolicy\":\"NO\",\"currency\":\"XAF\",\"roomBedAvailableDtoList"
                                        + "\":[]}]"));
    }

    @Test
    public void testGetAllRoomsByProperty() throws Exception {
        when(this.roomService.getAllRoomsByProperty(anyString())).thenReturn(new ArrayList<Room>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/public/rooms")
                .param("propertyId", "foo");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllRoomsByProperty2() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUserContactDetails(new UserContactDetails());
        user.setTokens(new ArrayList<ConfirmationToken>());
        user.setCountries(new ArrayList<Country>());
        user.setPropertyTypes(new ArrayList<PropertyType>());
        user.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setProperties(new ArrayList<Property>());
        user.setEnabled(true);
        user.setRole(new ArrayList<Role>());
        user.setRooms(new ArrayList<Room>());
        user.setId("42");
        user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user.setParkings(new ArrayList<Parking>());
        user.setCities(new ArrayList<City>());

        PropertyType propertyType = new PropertyType();
        propertyType.setUser(user);
        propertyType.setId("42");
        propertyType.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyType.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyType.setName("?");
        propertyType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        propertyType.setProperties(new ArrayList<Property>());
        propertyType.setDescription("The characteristics of someone or something");

        UserContactDetails userContactDetails = new UserContactDetails();
        userContactDetails.setLastName("Doe");
        userContactDetails.setUser(new User());
        userContactDetails.setId("42");
        userContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails.setPhoneNumber("4105551212");
        userContactDetails.setFirstName("Jane");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setUserContactDetails(userContactDetails);
        user1.setTokens(new ArrayList<ConfirmationToken>());
        user1.setCountries(new ArrayList<Country>());
        user1.setPropertyTypes(new ArrayList<PropertyType>());
        user1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setProperties(new ArrayList<Property>());
        user1.setEnabled(true);
        user1.setRole(new ArrayList<Role>());
        user1.setRooms(new ArrayList<Room>());
        user1.setId("42");
        user1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user1.setParkings(new ArrayList<Parking>());
        user1.setCities(new ArrayList<City>());

        Property property = new Property();
        property.setRating(0);
        property.setRoomReservationList(new ArrayList<RoomReservation>());
        property.setPropertyType(new PropertyType());
        property.setUser(new User());
        property.setParking(new Parking());
        property.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property.setCompletedRegistration(true);
        property.setName("?");
        property.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property.setPending(true);
        property.setPaymentOptions(new ArrayList<PaymentOption>());
        property.setRooms(new ArrayList<Room>());
        property.setId("42");
        property.setLanguages(new ArrayList<Language>());
        property.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property.setPropertyAddress(new PropertyAddress());
        property.setPropertyContactDetails(new PropertyContactDetails());
        property.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property.setBreakfast(new Breakfast());
        property.setExtraBed(new ExtraBed());
        property.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setPassword("iloveyou");
        user2.setUserContactDetails(new UserContactDetails());
        user2.setTokens(new ArrayList<ConfirmationToken>());
        user2.setCountries(new ArrayList<Country>());
        user2.setPropertyTypes(new ArrayList<PropertyType>());
        user2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setProperties(new ArrayList<Property>());
        user2.setEnabled(true);
        user2.setRole(new ArrayList<Role>());
        user2.setRooms(new ArrayList<Room>());
        user2.setId("42");
        user2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user2.setParkings(new ArrayList<Parking>());
        user2.setCities(new ArrayList<City>());

        Parking parking = new Parking();
        parking.setType(Type.PRIVATE);
        parking.setProperty(property);
        parking.setUser(user2);
        parking.setId("42");
        parking.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        parking.setAvailable(Available.NO);
        parking.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        parking.setReservation(Reservation.RESERVATION);
        parking.setSite(Site.ON_SITE);
        parking.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        parking.setCurrency(Currency.XAF);
        parking.setUnitPrice(10.0);

        Property property1 = new Property();
        property1.setRating(0);
        property1.setRoomReservationList(new ArrayList<RoomReservation>());
        property1.setPropertyType(new PropertyType());
        property1.setUser(new User());
        property1.setParking(new Parking());
        property1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property1.setCompletedRegistration(true);
        property1.setName("?");
        property1.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property1.setPending(true);
        property1.setPaymentOptions(new ArrayList<PaymentOption>());
        property1.setRooms(new ArrayList<Room>());
        property1.setId("42");
        property1.setLanguages(new ArrayList<Language>());
        property1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property1.setPropertyAddress(new PropertyAddress());
        property1.setPropertyContactDetails(new PropertyContactDetails());
        property1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property1.setBreakfast(new Breakfast());
        property1.setExtraBed(new ExtraBed());
        property1.setPolicy(new com.reservation.reservationservice.model.Policy());

        City city = new City();
        city.setUser(new User());
        city.setId("42");
        city.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        city.setPropertyAddresses(new ArrayList<PropertyAddress>());
        city.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        city.setName("?");
        city.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        city.setCountry(new Country());

        PropertyAddress propertyAddress = new PropertyAddress();
        propertyAddress.setProperty(property1);
        propertyAddress.setId("42");
        propertyAddress.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyAddress.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyAddress.setCode("?");
        propertyAddress.setCity(city);
        propertyAddress.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        propertyAddress.setAddressLine2("42 Main St");
        propertyAddress.setStreetAddress("42 Main St");

        Property property2 = new Property();
        property2.setRating(0);
        property2.setRoomReservationList(new ArrayList<RoomReservation>());
        property2.setPropertyType(new PropertyType());
        property2.setUser(new User());
        property2.setParking(new Parking());
        property2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property2.setCompletedRegistration(true);
        property2.setName("?");
        property2.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property2.setPending(true);
        property2.setPaymentOptions(new ArrayList<PaymentOption>());
        property2.setRooms(new ArrayList<Room>());
        property2.setId("42");
        property2.setLanguages(new ArrayList<Language>());
        property2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property2.setPropertyAddress(new PropertyAddress());
        property2.setPropertyContactDetails(new PropertyContactDetails());
        property2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property2.setBreakfast(new Breakfast());
        property2.setExtraBed(new ExtraBed());
        property2.setPolicy(new com.reservation.reservationservice.model.Policy());

        PropertyContactDetails propertyContactDetails = new PropertyContactDetails();
        propertyContactDetails.setProperty(property2);
        propertyContactDetails.setId("42");
        propertyContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        propertyContactDetails.setName("?");
        propertyContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        propertyContactDetails.setPhoneNumber("4105551212");
        propertyContactDetails.setCompanyName("?");
        propertyContactDetails.setAlternativeNumber("?");

        Property property3 = new Property();
        property3.setRating(0);
        property3.setRoomReservationList(new ArrayList<RoomReservation>());
        property3.setPropertyType(new PropertyType());
        property3.setUser(new User());
        property3.setParking(new Parking());
        property3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property3.setCompletedRegistration(true);
        property3.setName("?");
        property3.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property3.setPending(true);
        property3.setPaymentOptions(new ArrayList<PaymentOption>());
        property3.setRooms(new ArrayList<Room>());
        property3.setId("42");
        property3.setLanguages(new ArrayList<Language>());
        property3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property3.setPropertyAddress(new PropertyAddress());
        property3.setPropertyContactDetails(new PropertyContactDetails());
        property3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property3.setBreakfast(new Breakfast());
        property3.setExtraBed(new ExtraBed());
        property3.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setPassword("iloveyou");
        user3.setUserContactDetails(new UserContactDetails());
        user3.setTokens(new ArrayList<ConfirmationToken>());
        user3.setCountries(new ArrayList<Country>());
        user3.setPropertyTypes(new ArrayList<PropertyType>());
        user3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user3.setProperties(new ArrayList<Property>());
        user3.setEnabled(true);
        user3.setRole(new ArrayList<Role>());
        user3.setRooms(new ArrayList<Room>());
        user3.setId("42");
        user3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user3.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user3.setParkings(new ArrayList<Parking>());
        user3.setCities(new ArrayList<City>());

        Breakfast breakfast = new Breakfast();
        breakfast.setProperty(property3);
        breakfast.setUser(user3);
        breakfast.setId("42");
        breakfast.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        breakfast.setAvailable(Available.NO);
        breakfast.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        breakfast.setBreakfastAvailableList(new ArrayList<BreakfastAvailable>());
        breakfast.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        breakfast.setCurrency(Currency.XAF);
        breakfast.setUnitPrice(10.0);

        Property property4 = new Property();
        property4.setRating(0);
        property4.setRoomReservationList(new ArrayList<RoomReservation>());
        property4.setPropertyType(new PropertyType());
        property4.setUser(new User());
        property4.setParking(new Parking());
        property4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property4.setCompletedRegistration(true);
        property4.setName("?");
        property4.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property4.setPending(true);
        property4.setPaymentOptions(new ArrayList<PaymentOption>());
        property4.setRooms(new ArrayList<Room>());
        property4.setId("42");
        property4.setLanguages(new ArrayList<Language>());
        property4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property4.setPropertyAddress(new PropertyAddress());
        property4.setPropertyContactDetails(new PropertyContactDetails());
        property4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property4.setBreakfast(new Breakfast());
        property4.setExtraBed(new ExtraBed());
        property4.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setPassword("iloveyou");
        user4.setUserContactDetails(new UserContactDetails());
        user4.setTokens(new ArrayList<ConfirmationToken>());
        user4.setCountries(new ArrayList<Country>());
        user4.setPropertyTypes(new ArrayList<PropertyType>());
        user4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user4.setProperties(new ArrayList<Property>());
        user4.setEnabled(true);
        user4.setRole(new ArrayList<Role>());
        user4.setRooms(new ArrayList<Room>());
        user4.setId("42");
        user4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user4.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user4.setParkings(new ArrayList<Parking>());
        user4.setCities(new ArrayList<City>());

        ExtraBed extraBed = new ExtraBed();
        extraBed.setProperty(property4);
        extraBed.setUser(user4);
        extraBed.setGuestExtraBeds(new ArrayList<GuestExtraBed>());
        extraBed.setId("42");
        extraBed.setNumberOfExtraBeds(10);

        Property property5 = new Property();
        property5.setRating(0);
        property5.setRoomReservationList(new ArrayList<RoomReservation>());
        property5.setPropertyType(new PropertyType());
        property5.setUser(new User());
        property5.setParking(new Parking());
        property5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property5.setCompletedRegistration(true);
        property5.setName("?");
        property5.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property5.setPending(true);
        property5.setPaymentOptions(new ArrayList<PaymentOption>());
        property5.setRooms(new ArrayList<Room>());
        property5.setId("42");
        property5.setLanguages(new ArrayList<Language>());
        property5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property5.setPropertyAddress(new PropertyAddress());
        property5.setPropertyContactDetails(new PropertyContactDetails());
        property5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property5.setBreakfast(new Breakfast());
        property5.setExtraBed(new ExtraBed());
        property5.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setPassword("iloveyou");
        user5.setUserContactDetails(new UserContactDetails());
        user5.setTokens(new ArrayList<ConfirmationToken>());
        user5.setCountries(new ArrayList<Country>());
        user5.setPropertyTypes(new ArrayList<PropertyType>());
        user5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user5.setProperties(new ArrayList<Property>());
        user5.setEnabled(true);
        user5.setRole(new ArrayList<Role>());
        user5.setRooms(new ArrayList<Room>());
        user5.setId("42");
        user5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user5.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user5.setParkings(new ArrayList<Parking>());
        user5.setCities(new ArrayList<City>());

        com.reservation.reservationservice.model.Policy policy = new com.reservation.reservationservice.model.Policy();
        policy.setCheckInTo("alice.liddell@example.org");
        policy.setGuestCanCancel("?");
        policy.setProperty(property5);
        policy.setUser(user5);
        policy.setId("42");
        policy.setCheckOutTo("alice.liddell@example.org");
        policy.setCanAccommodateChildren(Default.NO);
        policy.setCheckOutFrom("jane.doe@example.org");
        policy.setGuestPay("?");
        policy.setCheckInFrom("jane.doe@example.org");

        Property property6 = new Property();
        property6.setRating(0);
        property6.setRoomReservationList(new ArrayList<RoomReservation>());
        property6.setPropertyType(propertyType);
        property6.setUser(user1);
        property6.setParking(parking);
        property6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property6.setCompletedRegistration(true);
        property6.setName("?");
        property6.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property6.setPending(true);
        property6.setPaymentOptions(new ArrayList<PaymentOption>());
        property6.setRooms(new ArrayList<Room>());
        property6.setId("42");
        property6.setLanguages(new ArrayList<Language>());
        property6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property6.setPropertyAddress(propertyAddress);
        property6.setPropertyContactDetails(propertyContactDetails);
        property6.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property6.setBreakfast(breakfast);
        property6.setExtraBed(extraBed);
        property6.setPolicy(policy);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setPassword("iloveyou");
        user6.setUserContactDetails(new UserContactDetails());
        user6.setTokens(new ArrayList<ConfirmationToken>());
        user6.setCountries(new ArrayList<Country>());
        user6.setPropertyTypes(new ArrayList<PropertyType>());
        user6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user6.setProperties(new ArrayList<Property>());
        user6.setEnabled(true);
        user6.setRole(new ArrayList<Role>());
        user6.setRooms(new ArrayList<Room>());
        user6.setId("42");
        user6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user6.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user6.setParkings(new ArrayList<Parking>());
        user6.setCities(new ArrayList<City>());

        UserContactDetails userContactDetails1 = new UserContactDetails();
        userContactDetails1.setLastName("Doe");
        userContactDetails1.setUser(user6);
        userContactDetails1.setId("42");
        userContactDetails1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails1.setPhoneNumber("4105551212");
        userContactDetails1.setFirstName("Jane");

        User user7 = new User();
        user7.setEmail("jane.doe@example.org");
        user7.setPassword("iloveyou");
        user7.setUserContactDetails(userContactDetails1);
        user7.setTokens(new ArrayList<ConfirmationToken>());
        user7.setCountries(new ArrayList<Country>());
        user7.setPropertyTypes(new ArrayList<PropertyType>());
        user7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user7.setProperties(new ArrayList<Property>());
        user7.setEnabled(true);
        user7.setRole(new ArrayList<Role>());
        user7.setRooms(new ArrayList<Room>());
        user7.setId("42");
        user7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user7.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user7.setParkings(new ArrayList<Parking>());
        user7.setCities(new ArrayList<City>());

        UserContactDetails userContactDetails2 = new UserContactDetails();
        userContactDetails2.setLastName("Doe");
        userContactDetails2.setUser(new User());
        userContactDetails2.setId("42");
        userContactDetails2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails2.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails2.setPhoneNumber("4105551212");
        userContactDetails2.setFirstName("Jane");

        User user8 = new User();
        user8.setEmail("jane.doe@example.org");
        user8.setPassword("iloveyou");
        user8.setUserContactDetails(userContactDetails2);
        user8.setTokens(new ArrayList<ConfirmationToken>());
        user8.setCountries(new ArrayList<Country>());
        user8.setPropertyTypes(new ArrayList<PropertyType>());
        user8.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user8.setProperties(new ArrayList<Property>());
        user8.setEnabled(true);
        user8.setRole(new ArrayList<Role>());
        user8.setRooms(new ArrayList<Room>());
        user8.setId("42");
        user8.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user8.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user8.setParkings(new ArrayList<Parking>());
        user8.setCities(new ArrayList<City>());

        User user9 = new User();
        user9.setEmail("jane.doe@example.org");
        user9.setPassword("iloveyou");
        user9.setUserContactDetails(new UserContactDetails());
        user9.setTokens(new ArrayList<ConfirmationToken>());
        user9.setCountries(new ArrayList<Country>());
        user9.setPropertyTypes(new ArrayList<PropertyType>());
        user9.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user9.setProperties(new ArrayList<Property>());
        user9.setEnabled(true);
        user9.setRole(new ArrayList<Role>());
        user9.setRooms(new ArrayList<Room>());
        user9.setId("42");
        user9.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user9.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user9.setParkings(new ArrayList<Parking>());
        user9.setCities(new ArrayList<City>());

        RoomType roomType = new RoomType();
        roomType.setRoomNames(new ArrayList<RoomName>());
        roomType.setUser(user9);
        roomType.setId("42");
        roomType.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomType.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomType.setName("?");
        roomType.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");

        Property property7 = new Property();
        property7.setRating(0);
        property7.setRoomReservationList(new ArrayList<RoomReservation>());
        property7.setPropertyType(new PropertyType());
        property7.setUser(new User());
        property7.setParking(new Parking());
        property7.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property7.setCompletedRegistration(true);
        property7.setName("?");
        property7.setPropertyFacilities(new ArrayList<PropertyFacility>());
        property7.setPending(true);
        property7.setPaymentOptions(new ArrayList<PaymentOption>());
        property7.setRooms(new ArrayList<Room>());
        property7.setId("42");
        property7.setLanguages(new ArrayList<Language>());
        property7.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        property7.setPropertyAddress(new PropertyAddress());
        property7.setPropertyContactDetails(new PropertyContactDetails());
        property7.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        property7.setBreakfast(new Breakfast());
        property7.setExtraBed(new ExtraBed());
        property7.setPolicy(new com.reservation.reservationservice.model.Policy());

        User user10 = new User();
        user10.setEmail("jane.doe@example.org");
        user10.setPassword("iloveyou");
        user10.setUserContactDetails(new UserContactDetails());
        user10.setTokens(new ArrayList<ConfirmationToken>());
        user10.setCountries(new ArrayList<Country>());
        user10.setPropertyTypes(new ArrayList<PropertyType>());
        user10.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user10.setProperties(new ArrayList<Property>());
        user10.setEnabled(true);
        user10.setRole(new ArrayList<Role>());
        user10.setRooms(new ArrayList<Room>());
        user10.setId("42");
        user10.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        user10.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        user10.setParkings(new ArrayList<Parking>());
        user10.setCities(new ArrayList<City>());

        RoomName roomName = new RoomName();
        roomName.setUser(new User());
        roomName.setId("42");
        roomName.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName.setName("?");
        roomName.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        roomName.setRoomType(new RoomType());
        roomName.setRoom(new Room());

        Room room = new Room();
        room.setProperty(property7);
        room.setUser(user10);
        room.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room.setName("?");
        room.setSmokingPolicy(com.reservation.reservationservice.constants.Policy.NO);
        room.setRoomSize(10.0);
        room.setRoomName(roomName);
        room.setAmenities(new ArrayList<Amenity>());
        room.setNumberOfRooms(10);
        room.setSize(Size.SQUARE_METRES);
        room.setId("42");
        room.setNumberOfGuests(10);
        room.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        room.setCurrency(Currency.XAF);
        room.setRoomBedAvailables(new ArrayList<RoomBedAvailable>());
        room.setUnitPrice(10.0);
        room.setRoomReservationItemList(new ArrayList<RoomReservationItem>());

        RoomName roomName1 = new RoomName();
        roomName1.setUser(user8);
        roomName1.setId("42");
        roomName1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        roomName1.setName("?");
        roomName1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        roomName1.setRoomType(roomType);
        roomName1.setRoom(room);

        Room room1 = new Room();
        room1.setProperty(property6);
        room1.setUser(user7);
        room1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room1.setName("?");
        room1.setSmokingPolicy(com.reservation.reservationservice.constants.Policy.NO);
        room1.setRoomSize(10.0);
        room1.setRoomName(roomName1);
        room1.setAmenities(new ArrayList<Amenity>());
        room1.setNumberOfRooms(10);
        room1.setSize(Size.SQUARE_METRES);
        room1.setId("42");
        room1.setNumberOfGuests(10);
        room1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        room1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        room1.setCurrency(Currency.XAF);
        room1.setRoomBedAvailables(new ArrayList<RoomBedAvailable>());
        room1.setUnitPrice(10.0);
        room1.setRoomReservationItemList(new ArrayList<RoomReservationItem>());

        ArrayList<Room> roomList = new ArrayList<Room>();
        roomList.add(room1);
        when(this.roomService.getAllRoomsByProperty(anyString())).thenReturn(roomList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/public/rooms")
                .param("propertyId", "foo");
        MockMvcBuilders.standaloneSetup(this.roomController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":\"42\",\"name\":\"?\",\"numberOfRooms\":10,\"roomSize\":10.0,\"unitPrice\":10.0,\"numberOfGuests\":10,"
                                        + "\"roomName\":\"?\",\"size\":\"SQUARE_METRES\",\"smokingPolicy\":\"NO\",\"currency\":\"XAF\",\"roomBedAvailableDtoList"
                                        + "\":[]}]"));
    }
}

