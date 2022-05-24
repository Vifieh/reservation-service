package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.config.security.JwtUtils;
import com.reservation.reservationservice.constants.ERole;
import com.reservation.reservationservice.dto.LoginDto;
import com.reservation.reservationservice.exception.BadRequestException;
import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.exception.TokenRefreshException;
import com.reservation.reservationservice.model.*;
import com.reservation.reservationservice.payload.RegisterPayload;
import com.reservation.reservationservice.repository.RefreshTokenRepository;
import com.reservation.reservationservice.repository.RoleRepository;
import com.reservation.reservationservice.repository.UserRepository;
import com.reservation.reservationservice.service.AuthenticationService;
import com.reservation.reservationservice.service.ConfirmationTokenService;
import com.reservation.reservationservice.service.EmailService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Util util = new Util();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    EmailService emailService;

    @Value("${reservation.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Value("${reservation.app.baseUrlLocal}")
    private String baseUrlLocal;


    @Override
    public void register(RegisterPayload registerPayload) {
        List<Role> roleList = new ArrayList<>();
        Email email = new Email();
        User user = new User();
        Optional<User> user1 = userRepository.findByEmail(registerPayload.getEmail());
        user.setId(util.generateId());
        user.setEmail(registerPayload.getEmail());
        user.setPassword(passwordEncoder.encode(registerPayload.getPassword()));
        user.setCreatedBy(registerPayload.getEmail());
        if (registerPayload.getRole().equals("USER")) {
            roleList.add(roleRepository.findByRole(ERole.ROLE_USER));
        }
        if (user1.isPresent() || registerPayload.getRole().equals("MANAGER")) {
            roleList.add(roleRepository.findByRole(ERole.ROLE_MANAGER));
        }
        user.setRole(roleList);
        ConfirmationToken confirmationToken = new ConfirmationToken(
                util.generateToken(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        String link = baseUrlLocal + "api/v1/public/auth/confirm?token=" + confirmationToken.getToken();
        emailService.send(user, email,  link);
        userRepository.save(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    @Transactional
    @Override
    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.get().getConfirmedAt() != null) {
            throw new ResourceAlreadyExistException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.get().getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("token expired");
        }
        confirmationTokenService.setConfirmedAt(token);
        enableUser(confirmationToken.get().getUser().getEmail());
        return "Account Confirmed";
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Override
    public void resendVerification(String email) {
        Optional<User> user1 = userRepository.findByEmail(email);
        Email email1 = new Email();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                util.generateToken(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user1.get()
        );
        String link = baseUrlLocal + "api/v1/public/auth/confirm?token=" + confirmationToken.getToken();
        if(user1.get().getTokens().get(0).getConfirmedAt() == null) {
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            emailService.send(user1.get(), email1, link);
        }
    }


    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }


    @Override
    public LoginDto login(User user) {
        User user1 = checkEmail(user.getEmail());
        if (!user1.getEnabled()) {
            throw new BadRequestException(user.getEmail() + " has nas not yet confirmed the account");
        }
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        }catch (BadCredentialsException ex) {
            throw new BadRequestException("Invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken = createRefreshToken(userDetails.getUser().getId());

        return new LoginDto(jwt, refreshToken.getToken(), userDetails.getUser().getId(), refreshToken.getExpiryDate(),
                userDetails.getUsername(),roles);
    }

    @Override
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public User checkEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        user.orElseThrow(() -> new ResourceNotFoundException("User does not exist with email: "+ email));
        return user.get();
    }
}
