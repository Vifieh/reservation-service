package com.reservation.reservationservice.repository;

import com.reservation.reservationservice.model.ERole;
import com.reservation.reservationservice.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1L);
        role.setRole(ERole.ROLE_USER);
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void it_should_check_when_role_exist() {
        //given
        ERole eRole = ERole.ROLE_USER;
        //when
        Role role = roleRepository.findByRole(eRole);
        //then
        assertThat(role.getRole()).isEqualTo(eRole);
    }

    @Test
    void it_should_check_when_role_does_not_exist() {
        //given
        ERole eRole = ERole.ROLE_ADMIN;
        //then
        assertThat(roleRepository.findByRole(eRole)).isNull();
    }
}