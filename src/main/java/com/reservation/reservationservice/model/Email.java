package com.reservation.reservationservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class Email {

    String from = "test@gmail.com";
    String subject = "no-reply, Confirm your email";
    String template = "registration.html";
}