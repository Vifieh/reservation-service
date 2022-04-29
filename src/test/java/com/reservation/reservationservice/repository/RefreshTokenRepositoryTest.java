package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.RefreshToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @BeforeEach
    void setUp() {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken("token");
        refreshToken.setExpiryDate(Instant.now());
        refreshTokenRepository.save(refreshToken);
    }

    @AfterEach
    void tearDown() {
        refreshTokenRepository.deleteAll();
    }

    @Test
    void it_should_check_when_refreshToken_exist() {
        //given
        String token = "token";
        //when
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        //then
        assertThat(refreshToken).isPresent();
    }

    @Test
    void it_should_check_when_refreshToken_does_not_exist() {
        //given
        String token = "token1";
        //when
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        //then
        assertThat(refreshToken).isNotPresent();
    }
}