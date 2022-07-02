package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.RoomReservationItemPayload;
import com.reservation.reservationservice.payload.RoomReservationPayload;
import com.reservation.reservationservice.repository.ReservationContactDetailsRepository;
import com.reservation.reservationservice.repository.RoomRepository;
import com.reservation.reservationservice.repository.RoomReservationItemRepository;
import com.reservation.reservationservice.repository.RoomReservationRepository;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/25/22
 */
@Service
public class RoomReservationServiceImpl implements RoomReservationService {
    private final Util util = new Util();

    @Autowired
    RoomReservationRepository reservationRepository;

    @Autowired
    RoomReservationItemRepository reservationItemRepository;

    @Autowired
    ReservationContactDetailsRepository contactDetailsRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomService roomService;

    @Autowired
    CountryService countryService;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @Autowired
    PropertyService propertyService;

    @Override
    public void reserveRoom(String propertyId, RoomReservationPayload reservationPayload) {
        RoomReservation roomReservation = new RoomReservation();
        User user = userService.getAuthUser();
        Property property = propertyService.getProperty(propertyId);
        roomReservation.setId(util.generateId());
        roomReservation.setUser(user);
        roomReservation.setProperty(property);
        roomReservation.setCheckIn(reservationPayload.getCheckIn());
        roomReservation.setTotalPrice(reservationPayload.getTotalPrice());
        roomReservation.setCurrency(reservationPayload.getCurrency());
        roomReservation.setCheckOut(reservationPayload.getCheckOut());
        roomReservation.setNumberOfAdults(reservationPayload.getNumberOfAdults());
        roomReservation.setNumberOfChildren(reservationPayload.getNumberOfChildren());
        roomReservation.setSpecialRequest(reservationPayload.getSpecialRequest());
        roomReservation.setArrivalTime(reservationPayload.getArrivalTime());
        roomReservation.setCreatedBy(user.getEmail());
        RoomReservation savedReservation = reservationRepository.save(roomReservation);
        ReservationContactDetails contactDetails = addReservationContactDetails(savedReservation, reservationPayload);
        addRoomReservationItem(savedReservation, reservationPayload, contactDetails);
    }

    @Override
    public List<RoomReservation> getRoomReservationsByProperty(String propertyId) {
        Property property = propertyService.getProperty(propertyId);
        return reservationRepository.findByPropertyOrderByCheckInDesc(property);
    }


    private void addRoomReservationItem(RoomReservation reservation, RoomReservationPayload reservationPayload,
                                        ReservationContactDetails contactDetails) {
        Email reservationEmail = new Email();
        reservationPayload.getReservationItemPayloadList().forEach(reservationItemPayload -> {
           Room room = roomService.getRoom(reservationItemPayload.getRoomId());
            RoomReservationItem roomReservationItem = new RoomReservationItem();
            roomReservationItem.setId(util.generateId());
            roomReservationItem.setPrice(reservationItemPayload.getPrice());
            roomReservationItem.setCurrency(reservationItemPayload.getCurrency());
            roomReservationItem.setNumberOfRooms(reservationItemPayload.getNumberOfRooms());
            roomReservationItem.setFullGuestName(reservationItemPayload.getFullGuestName());
            roomReservationItem.setGuestEmail(reservationItemPayload.getGuestEmail());
            roomReservationItem.setRoom(room);
            roomReservationItem.setRoomReservation(reservation);
            room.setNumberOfRooms(room.getNumberOfRooms() - roomReservationItem.getNumberOfRooms());
            roomRepository.save(room);
            reservationItemRepository.saveAndFlush(roomReservationItem);
            emailService.sendReservationCompletedEmail(contactDetails, reservationPayload, reservationEmail, room);
        });
    }

    private ReservationContactDetails addReservationContactDetails( RoomReservation reservation,
                                                                    RoomReservationPayload reservationPayload) {
        Country country = countryService.getCountry(reservationPayload.getContactDetailsPayload().getCountryId());
        ReservationContactDetails contactDetails = new ReservationContactDetails();
        contactDetails.setId(util.generateId());
        contactDetails.setFirstName(reservationPayload.getContactDetailsPayload().getFirstName());
        contactDetails.setLastName(reservationPayload.getContactDetailsPayload().getLastName());
        contactDetails.setEmail(reservationPayload.getContactDetailsPayload().getEmail());
        contactDetails.setPhoneNumber(reservationPayload.getContactDetailsPayload().getPhoneNumber());
        contactDetails.setRoomReservation(reservation);
        contactDetails.setCountry(country);
        return contactDetailsRepository.save(contactDetails);
    }
}
