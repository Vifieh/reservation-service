package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.ConfirmationToken;
import com.reservation.reservationservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ConfirmationTokenRepositoryTest {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @BeforeEach
    void setUp() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setId("1");
        confirmationToken.setToken("token");
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        confirmationTokenRepository.save(confirmationToken);
    }

    @AfterEach
    void tearDown() {
        confirmationTokenRepository.deleteAll();
    }

    @Test
    void it_should_check_when_token_exist() {
        //given
        String token = "token";
        //when
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        //then
        assertThat(confirmationToken).isPresent();
    }

    @Test
    void it_should_check_when_token_does_not_exist() {
        //given
        String token = "token1";
        //when
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        //then
        assertThat(confirmationToken).isNotPresent();
    }

    @Test
    void it_should_updateConfirmedAt() {
        //given
        String token = "token";
        LocalDateTime confirmedAt = LocalDateTime.now();
        //when
        int expected = confirmationTokenRepository.updateConfirmedAt(token, confirmedAt);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    void it_should_not_updateConfirmedAt() {
        //given
        String token = "token1";
        LocalDateTime confirmedAt = LocalDateTime.now();
        //when
        int expected = confirmationTokenRepository.updateConfirmedAt(token, confirmedAt);
        //then
        assertThat(expected).isEqualTo(0);
    }
}