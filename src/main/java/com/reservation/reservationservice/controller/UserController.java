package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.dto.ContactDetailsDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.UserDetailsDto;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.model.UserContactDetails;
import com.reservation.reservationservice.payload.ContactDetailsPayload;
import com.reservation.reservationservice.payload.UserPayload;
import com.reservation.reservationservice.service.ContactDetailsService;
import com.reservation.reservationservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/protected/")
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ContactDetailsService contactDetailsService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("users/user_profile")
    public ResponseEntity<UserDetailsDto> getUserProfile() {
       User user = userService.getAuthUser();
        ContactDetailsDto contactDetailsDto = convertContactDetailsToContactDetailsDto(user);
       UserDetailsDto userDetailsDto = new UserDetailsDto(user.getId(), user.getEmail(),
               contactDetailsDto, user.getRole());
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @PostMapping("users/add_userContactDetails")
    public ResponseEntity<ResponseMessage> addUserContactDetails(@RequestBody @Valid
                                                                             ContactDetailsPayload contactDetailsPayload) {
        UserContactDetails userContactDetails = this.modelMapper.map(contactDetailsPayload, UserContactDetails.class);
        contactDetailsService.addContactDetails(userContactDetails);
        message = "Contact details added successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @PutMapping("users/edit-profile")
    public ResponseEntity<ResponseMessage> editUserProfile(@RequestBody @Valid UserPayload userPayload) {
        User user = this.modelMapper.map(userPayload, User.class);
        userService.editUserProfile(user);
        message = "Profile edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users")
    public ResponseEntity<List<UserDetailsDto>> getUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDetailsDto> userDetailsDtos = convertUsersToUserDetailDtos(users);
        return new ResponseEntity<>(userDetailsDtos, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users/role_user")
    public ResponseEntity<List<UserDetailsDto>> getUsersByRole(@RequestParam ERole eRole) {
        List<User> users = userService.getAllUsersByRole(eRole);
        List<UserDetailsDto> userDetailsDtos = convertUsersToUserDetailDtos(users);
        return new ResponseEntity<>(userDetailsDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("users/{userId}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable("userId") String userId) {
        User user = userService.getUser(userId);
        ContactDetailsDto contactDetailsDto = convertContactDetailsToContactDetailsDto(user);
        UserDetailsDto userDetailsDto = new UserDetailsDto(user.getId(), user.getEmail(),
                contactDetailsDto, user.getRole());
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }

    private ContactDetailsDto convertContactDetailsToContactDetailsDto(User user) {
        return modelMapper.map(user.getUserContactDetails(), ContactDetailsDto.class);
    }

    private List<UserDetailsDto> convertUsersToUserDetailDtos(List<User> users) {
        return users.stream().map(user ->
                        this.modelMapper.map(user, UserDetailsDto.class)).collect(Collectors.toList());
    }
}
