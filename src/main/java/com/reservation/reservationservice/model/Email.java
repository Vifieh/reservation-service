package com.reservation.reservationservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class Email {

    @Schema(example = "test@gmail.com")
    String from;
    @Schema(example = "no-reply, Confirm your email")
    String subject;
    @Schema(example = "registration.html")
    String template;
}