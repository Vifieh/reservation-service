package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.ConfirmationToken;
import com.reservation.reservationservice.repository.ConfirmationTokenRepository;
import com.reservation.reservationservice.service.ConfirmationTokenService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final Util util = new Util();
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationToken.setId(util.generateId());
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        confirmationToken.orElseThrow(() -> new ResourceNotFoundException("token not found"));
        return confirmationToken;
    }

    @Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
