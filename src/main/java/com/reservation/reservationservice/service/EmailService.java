package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.Email;
import com.reservation.reservationservice.model.User;

public interface EmailService {

    void send(User user, Email email, String link);
}
