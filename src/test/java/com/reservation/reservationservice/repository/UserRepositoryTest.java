package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setEnabled(false);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void it_should_check_when_users_email_exist() {
        //given
        String email = "test@gmail.com";
        //when
       Optional<User> user1 = userRepository.findByEmail(email);
        //then
        assertThat(user1).isPresent();
    }

    @Test
    void it_should_check_when_users_email_does_not_exist() {
        //given
        String email = "test1@gmail.com";
        //when
        Optional<User> user1 = userRepository.findByEmail(email);
        //then
        assertThat(user1).isNotPresent();
    }

}
