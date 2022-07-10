package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    String from = "reservationservice2022@gmail.com";
    String subjectConfirmAccount = "no-reply, Confirm your account";
    String subjectCompletedRegistration = "no-reply, Congrats! You've completed your registration";
    String subjectCompletedRegistrationToAdmin = "no-reply, Property registration completed";
    String subjectApprovedProperty = "no-reply, Congrats! Your property has been approved";
    String subjectBookingCompleted = "no-reply, Congrats! Your booking is completed";
    String subjectBookingCompletedToManager = "no-reply, Booking is completed";
    String registrationTemplate = "registration.html";
    String completedRegistrationTemplate = "completed-registration.html";
    String completedRegistrationToAdminTemplate = "completed-registration-to-admin.html";
    String approvedPropertyTemplate = "property-approved.html";
    String bookingCompletedTemplate = "booking-completed.html";
    String bookingCompletedToManagerTemplate = "booking-completed-to-manager.html";
}

