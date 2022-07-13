package com.reservation.reservationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.ConfirmationToken;
import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.model.Parking;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.PropertyType;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.model.Room;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.model.UserContactDetails;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.CountryService;
import com.reservation.reservationservice.service.serviceImpl.CountryServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CountryController.class})
@ExtendWith(MockitoExtension.class)
class CountryControllerTest {
    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testCreateCountry() throws Exception {
        UserContactDetails userContactDetails = new UserContactDetails();
        userContactDetails.setLastName("Doe");
        userContactDetails.setUser(new User());
        userContactDetails.setId("42");
        userContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails.setPhoneNumber("4105551212");
        userContactDetails.setFirstName("Jane");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUserContactDetails(userContactDetails);
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

        UserContactDetails userContactDetails1 = new UserContactDetails();
        userContactDetails1.setLastName("Doe");
        userContactDetails1.setUser(user);
        userContactDetails1.setId("42");
        userContactDetails1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails1.setPhoneNumber("4105551212");
        userContactDetails1.setFirstName("Jane");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setUserContactDetails(userContactDetails1);
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

        Country country = new Country();
        country.setUser(user1);
        country.setId("42");
        country.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setName("?");
        country.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        country.setCities(new ArrayList<City>());
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(country);
        doNothing().when(this.countryService).createCountry((Country) any());

        CustomPayload customPayload = new CustomPayload();
        customPayload.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customPayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/protected/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Country created successfully!\"}"));
    }

    @Test
    public void testEditCountry() throws Exception {
        UserContactDetails userContactDetails = new UserContactDetails();
        userContactDetails.setLastName("Doe");
        userContactDetails.setUser(new User());
        userContactDetails.setId("42");
        userContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails.setPhoneNumber("4105551212");
        userContactDetails.setFirstName("Jane");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUserContactDetails(userContactDetails);
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

        UserContactDetails userContactDetails1 = new UserContactDetails();
        userContactDetails1.setLastName("Doe");
        userContactDetails1.setUser(user);
        userContactDetails1.setId("42");
        userContactDetails1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails1.setPhoneNumber("4105551212");
        userContactDetails1.setFirstName("Jane");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setUserContactDetails(userContactDetails1);
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

        Country country = new Country();
        country.setUser(user1);
        country.setId("42");
        country.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setName("?");
        country.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        country.setCities(new ArrayList<City>());
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(country);
        doNothing().when(this.countryService).editCountry(anyString(), (Country) any());

        CustomPayload customPayload = new CustomPayload();
        customPayload.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customPayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch("/api/v1/protected/countries/{countryId}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Country edited successfully\"}"));
    }

    @Test
    public void testGetCountry() throws Exception {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(new CustomDto("42", "Name"));

        UserContactDetails userContactDetails = new UserContactDetails();
        userContactDetails.setLastName("Doe");
        userContactDetails.setUser(new User());
        userContactDetails.setId("42");
        userContactDetails.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails.setPhoneNumber("4105551212");
        userContactDetails.setFirstName("Jane");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setPassword("iloveyou");
        user.setUserContactDetails(userContactDetails);
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

        UserContactDetails userContactDetails1 = new UserContactDetails();
        userContactDetails1.setLastName("Doe");
        userContactDetails1.setUser(user);
        userContactDetails1.setId("42");
        userContactDetails1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userContactDetails1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        userContactDetails1.setPhoneNumber("4105551212");
        userContactDetails1.setFirstName("Jane");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setPassword("iloveyou");
        user1.setUserContactDetails(userContactDetails1);
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

        Country country = new Country();
        country.setUser(user1);
        country.setId("42");
        country.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setName("Name");
        country.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        country.setCities(new ArrayList<City>());
        when(this.countryService.getCountry(anyString())).thenReturn(country);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/public/countries/{countryId}",
                "42");
        MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"createdAt\":null,\"updatedAt\":null,\"createdBy\":null,\"id\":\"42\",\"name\":\"Name\"}"));
    }

    @Test
    public void testDeleteCountry() throws Exception {
        doNothing().when(this.countryService).deleteCountry(anyString());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/protected/countries/{countryId}", "42");
        MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Country deleted successfully\"}"));
    }

    @Test
    public void testDeleteCountry2() throws Exception {
        doNothing().when(this.countryService).deleteCountry(anyString());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
                .delete("/api/v1/protected/countries/{countryId}", "42");
        deleteResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"Country deleted successfully\"}"));
    }

    @Test
    public void testGetCountries() throws Exception {
        when(this.countryService.getAllCountries()).thenReturn(new ArrayList<Country>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/public/countries");
        MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetCountries2() throws Exception {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any())).thenReturn(new CustomDto("42", "Name"));

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

        UserContactDetails userContactDetails = new UserContactDetails();
        userContactDetails.setLastName("Doe");
        userContactDetails.setUser(user);
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

        Country country = new Country();
        country.setUser(user1);
        country.setId("42");
        country.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        country.setName("?");
        country.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        country.setCities(new ArrayList<City>());

        ArrayList<Country> countryList = new ArrayList<Country>();
        countryList.add(country);
        when(this.countryService.getAllCountries()).thenReturn(countryList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/public/countries");
        MockMvcBuilders.standaloneSetup(this.countryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"createdAt\":null,\"updatedAt\":null,\"createdBy\":null,\"id\":\"42\",\"name\":\"Name\"}]"));
    }

    @InjectMocks
    CountryController countryController;

    @Mock
    CountryServiceImpl countryService;

    CustomPayload countryPayload;
    Country country;

//    @Test
//    void createCountry_should_call_countryService() {
//        countryPayload = new CustomPayload();
//        country = new Country();
//        ResponseEntity responseEntity = countryController.createCountry(countryPayload);
//        verify(countryService).createCountry(country);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//    }
}