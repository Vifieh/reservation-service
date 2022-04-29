package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.UserRepository;
import com.reservation.reservationservice.service.AuthenticationService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private Util util = new Util();

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authenticationService.checkEmail(userDetails.getUsername());
    }

    @Override
    public User getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new ResourceNotFoundException("User not present with id- " + userId));
        return user.get();
    }

}
