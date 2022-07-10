package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.Role;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.UserRepository;
import com.reservation.reservationservice.service.AuthenticationService;
import com.reservation.reservationservice.service.ContactDetailsService;
import com.reservation.reservationservice.service.RoleService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Util util = new Util();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    ContactDetailsService contactDetailsService;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return authenticationService.checkEmail(userDetails.getUsername());
    }

    @Override
    public void editUserProfile(User user) {
        User user1 = getAuthUser();
        user1.setEmail(user.getEmail());
        contactDetailsService.editContactDetails(user1.getUserContactDetails().getId(), user.getUserContactDetails());
        userRepository.save(user1);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUsersByRole(ERole eRole) {
        Role role = roleService.getRoleByName(eRole);
        return role.getUser();
    }

    @Override
    public User getUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new ResourceNotFoundException("User not present with id- " + userId));
        return user.get();
    }

}
