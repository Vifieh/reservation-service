package com.reservation.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    String from = "test@gmail.com";
    String subjectConfirmAccount = "no-reply, Confirm your account";
    String subjectCompletedRegistration = "no-reply, Congrats! You've completed your registration";
    String subjectApprovedProperty = "no-reply, Congrats! Your property has been approved";
    String subjectBookingCompleted = "no-reply, Congrats! Your booking is completed";
    String registrationTemplate = "registration.html";
    String completedRegistrationTemplate = "completed-registration.html";
    String approvedPropertyTemplate = "property-approved.html";
    String bookingCompletedTemplate = "booking-completed.html";
}

