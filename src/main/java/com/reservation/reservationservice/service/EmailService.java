package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.RoomReservationPayload;

public interface EmailService {

    void sendUserRegistration(User user, Email email, String link);

    void sendCompletedRegistration(User user, Email email, Property property);

    void sendCompletedRegistrationToAdmin(User user, Email email, Property property, String adminEmail);

    void approvedPropertyEmail(User user, Email email, Property property);

    void sendReservationCompletedEmail(ReservationContactDetails contactDetails, RoomReservationPayload reservationPayload,
                                       Email email, Room room);

    void sendReservationCompletedEmailToManager(ReservationContactDetails contactDetails, RoomReservationPayload reservationPayload,
                                               Email email, Room room, String propertyEmail);
}
