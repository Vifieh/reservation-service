package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.User;

public interface UserService {

    User getAuthUser();

    User getUser(String userId);

}
