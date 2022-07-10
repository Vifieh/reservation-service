package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.BadRequestException;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.RoomReservationPayload;
import com.reservation.reservationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendUserRegistration(User user, Email email, String link) {
        try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        context.setVariable("name", user.getEmail().substring(0, user.getEmail().indexOf("@")));
        context.setVariable("link", link);
        helper.setFrom(email.getFrom());
        helper.setTo(user.getEmail());
        helper.setSubject(email.getSubjectConfirmAccount());
        String html = templateEngine.process(email.getRegistrationTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new BadRequestException("failed to send email");
        }
    }

    @Override
    public void sendCompletedRegistration(User user, Email email, Property property) {
        try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("name", user.getEmail().substring(0, user.getEmail().indexOf("@")));
        context.setVariable("propertyName", property.getName());
        context.setVariable("propertyId", property.getId());
        helper.setFrom(email.getFrom());
        helper.setTo(user.getEmail());
        helper.setSubject(email.getSubjectCompletedRegistration());
        String html = templateEngine.process(email.getCompletedRegistrationTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new BadRequestException("failed to send email");
        }
    }

    @Override
    public void sendCompletedRegistrationToAdmin(User user, Email email, Property property, String adminEmail) {
        try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("name", user.getEmail().substring(0, user.getEmail().indexOf("@")));
        context.setVariable("email", user.getEmail());
        context.setVariable("propertyName", property.getName());
        context.setVariable("propertyId", property.getId());
        helper.setFrom(email.getFrom());
        helper.setTo(adminEmail);
        helper.setSubject(email.getSubjectCompletedRegistrationToAdmin());
        String html = templateEngine.process(email.getCompletedRegistrationToAdminTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new BadRequestException("failed to send email");
        }
    }

    @Override
    public void approvedPropertyEmail(User user, Email email, Property property) {
        try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("name", user.getEmail().substring(0, user.getEmail().indexOf("@")));
        context.setVariable("propertyName", property.getName());
        helper.setFrom(email.getFrom());
        helper.setTo(user.getEmail());
        helper.setSubject(email.getSubjectApprovedProperty());
        String html = templateEngine.process(email.getApprovedPropertyTemplate(), context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new BadRequestException("failed to send email");
        }
    }

    @Override
    public void sendReservationCompletedEmail(ReservationContactDetails contactDetails, RoomReservationPayload reservationPayload,
                                              Email email, Room room) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("propertyName", room.getProperty().getName());
            context.setVariable("roomName", room.getRoomName().getName());
            context.setVariable("numberOfAdults", reservationPayload.getNumberOfAdults());
            context.setVariable("numberOfChildren", reservationPayload.getNumberOfChildren());
            context.setVariable("totalPrice", reservationPayload.getTotalPrice());
            context.setVariable("name", contactDetails.getFirstName());
            context.setVariable("email", contactDetails.getEmail());
            helper.setFrom(email.getFrom());
            helper.setTo(contactDetails.getEmail());
            helper.setSubject(email.getSubjectBookingCompleted());
            String html = templateEngine.process(email.getBookingCompletedTemplate(), context);
            helper.setText(html, true);

            log.info("Sending email: {} with html body: {}", email, html);
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new BadRequestException("failed to send email");
        }
    }

    @Override
    public void sendReservationCompletedEmailToManager(ReservationContactDetails contactDetails, RoomReservationPayload reservationPayload,
                                              Email email, Room room, String propertyEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariable("propertyName", room.getProperty().getName());
            context.setVariable("roomName", room.getRoomName().getName());
            context.setVariable("numberOfAdults", reservationPayload.getNumberOfAdults());
            context.setVariable("numberOfChildren", reservationPayload.getNumberOfChildren());
            context.setVariable("totalPrice", reservationPayload.getTotalPrice());
            context.setVariable("name", contactDetails.getFirstName());
            context.setVariable("email", contactDetails.getEmail());
            helper.setFrom(email.getFrom());
            helper.setTo(propertyEmail);
            helper.setSubject(email.getSubjectBookingCompletedToManager());
            String html = templateEngine.process(email.getBookingCompletedToManagerTemplate(), context);
            helper.setText(html, true);

            log.info("Sending email: {} with html body: {}", email, html);
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new BadRequestException("failed to send email");
        }
    }
}
