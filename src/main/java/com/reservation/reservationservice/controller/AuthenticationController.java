package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.config.security.JwtUtils;
import com.reservation.reservationservice.dto.LoginDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.dto.TokenRefreshDto;
import com.reservation.reservationservice.exception.TokenRefreshException;
import com.reservation.reservationservice.model.RefreshToken;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.payload.LoginPayload;
import com.reservation.reservationservice.payload.RegisterPayload;
import com.reservation.reservationservice.payload.TokenRefreshPayload;
import com.reservation.reservationservice.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/public/auth/")
@RestController
@CrossOrigin()
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    String message = null;

    @PostMapping("register")
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody RegisterPayload registerPayload) {
        authenticationService.register(registerPayload);
        message = "User registered Successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginPayload loginPayload) {
        User user = this.modelMapper.map(loginPayload, User.class);
        LoginDto loginDTO = authenticationService.login(user);
        return new ResponseEntity<>(loginDTO, HttpStatus.CREATED);
    }

    @GetMapping("confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        String message = authenticationService.confirmToken(token);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("resend-verification-link")
    public ResponseEntity<ResponseMessage> resendVerification(@Valid @RequestBody String email) {
        authenticationService.resendVerification(email);
        message = "Email sent successfully";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshPayload request) {
        String requestRefreshToken = request.getRefreshToken();

        return authenticationService.findByToken(requestRefreshToken).map(authenticationService::verifyExpiration)
                .map(RefreshToken::getUser).map(user -> {
                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
                    return ResponseEntity.ok(new TokenRefreshDto(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }
}
