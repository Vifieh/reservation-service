package com.reservation.reservationservice.service.serviceImpl;


import com.reservation.reservationservice.dto.RoomReservationDTO;
import com.reservation.reservationservice.exception.BadRequestException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.RoomReservationPayload;
import com.reservation.reservationservice.repository.ReservationContactDetailsRepository;
import com.reservation.reservationservice.repository.RoomRepository;
import com.reservation.reservationservice.repository.RoomReservationItemRepository;
import com.reservation.reservationservice.repository.RoomReservationRepository;
import com.reservation.reservationservice.service.*;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public RoomReservation reserveRoom(String propertyId, RoomReservationPayload reservationPayload) {
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
        String ref="RS"+user.getId() +propertyId+ LocalDateTime.now();
        roomReservation.setRef(ref);
        RoomReservation savedReservation = reservationRepository.save(roomReservation);
        ReservationContactDetails contactDetails = addReservationContactDetails(savedReservation, reservationPayload);
        addRoomReservationItem(savedReservation, reservationPayload, contactDetails, property);
        return savedReservation;
    }

    @Override
    public List<RoomReservation> getRoomReservationsByProperty(String propertyId, boolean hasCheckedOut) {
        Property property = propertyService.getProperty(propertyId);
        return reservationRepository.findByPropertyAndHasCheckedOutOrderByCheckInDesc(property, hasCheckedOut);
    }

    @Override
    public RoomReservation getRoomReservation(String roomReservationId) {
        Optional<RoomReservation> optionalRoomReservation = reservationRepository.findById(roomReservationId);
        optionalRoomReservation.orElseThrow(() ->
                new ResourceNotFoundException("No reservation found with id - " + roomReservationId));
        return optionalRoomReservation.get();
    }

    @Override
    public void checkOutGuest(String roomReservationId) {
        User user = userService.getAuthUser();
        RoomReservation roomReservation = getRoomReservation(roomReservationId);
        roomReservation.setHasCheckedOut(true);
        roomReservation.setCheckedOutBy(user.getEmail());
        roomReservation.getRoomReservationItemList().forEach(roomReservationItem -> {
            Room room = roomService.getRoom(roomReservationItem.getRoom().getId());
            room.setNumberOfRooms(room.getNumberOfRooms() + roomReservationItem.getNumberOfRooms());
            roomRepository.save(room);
        });
        reservationRepository.save(roomReservation);
    }

    private void addRoomReservationItem(RoomReservation reservation, RoomReservationPayload reservationPayload,
                                        ReservationContactDetails contactDetails, Property property) {
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
            if (room.getNumberOfRooms() == 0) {
                throw new BadRequestException("No rooms available of this type");
            }
            roomReservationItem.setRoom(room);
            roomReservationItem.setRoomReservation(reservation);
            room.setNumberOfRooms(room.getNumberOfRooms() - roomReservationItem.getNumberOfRooms());
            roomRepository.save(room);
            reservationItemRepository.saveAndFlush(roomReservationItem);
            emailService.sendReservationCompletedEmail(contactDetails, reservationPayload, reservationEmail, room);
            emailService.sendReservationCompletedEmailToManager(contactDetails, reservationPayload, reservationEmail, room, property.getCreatedBy());
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
