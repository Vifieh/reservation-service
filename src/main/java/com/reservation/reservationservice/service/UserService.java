package com.reservation.reservationservice.service;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.model.User;

import java.util.List;

public interface UserService {

    User getAuthUser();

    void editUserProfile(User user);

    List<User> getAllUsers();

    List<User> getAllUsersByRole(ERole eRole);

    User getUser(String userId);

}
