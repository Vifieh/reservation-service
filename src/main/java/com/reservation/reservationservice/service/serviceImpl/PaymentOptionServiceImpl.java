package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.PaymentOption;
import com.reservation.reservationservice.model.Property;
import com.reservation.reservationservice.model.User;
import com.reservation.reservationservice.repository.PaymentOptionRepository;
import com.reservation.reservationservice.service.PaymentOptionService;
import com.reservation.reservationservice.service.PropertyService;
import com.reservation.reservationservice.service.UserService;
import com.reservation.reservationservice.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Vifieh Ruth
 * on 5/24/22
 */

@Service
public class PaymentOptionServiceImpl implements PaymentOptionService {
    private Util util = new Util();

    @Autowired
    PaymentOptionRepository paymentOptionRepository;

    @Autowired
    UserService userService;

    @Autowired
    PropertyService propertyService;

    @Override
    public void createPaymentOption(PaymentOption paymentOption) {
        User user = userService.getAuthUser();
        Optional<PaymentOption> paymentOption1= paymentOptionRepository.findByName(paymentOption.getName());
        if (paymentOption1.isPresent()) {
            throw new ResourceAlreadyExistException("PaymentOption already exist");
        }
        paymentOption.setId(util.generateId());
        paymentOption.setUser(user);
        paymentOptionRepository.save(paymentOption);
    }

    @Override
    public void editPaymentOption(String paymentOptionId, PaymentOption paymentOption) {
        PaymentOption paymentOption1 = getPaymentOption(paymentOptionId);
        paymentOption.setName(paymentOption.getName());
        paymentOptionRepository.save(paymentOption1);
    }

    @Override
    public List<PaymentOption> getAllPaymentOptions() {
        return paymentOptionRepository.findAll();
    }

    @Override
    public List<PaymentOption> getAllPaymentOptionsByProperty(String propertyId) {
        Property property = propertyService.getProperty(propertyId);
        return property.getPaymentOptions();
    }

    @Override
    public PaymentOption getPaymentOption(String paymentOptionId) {
        Optional<PaymentOption> paymentOption = paymentOptionRepository.findById(paymentOptionId);
        paymentOption.orElseThrow(() -> new ResourceNotFoundException("PaymentOption not found with id -  " + paymentOptionId));
        return paymentOption.get();
    }

    @Override
    public PaymentOption getPaymentOptionByName(String name) {
        Optional<PaymentOption> paymentOption = paymentOptionRepository.findByName(name);
        paymentOption.orElseThrow(() -> new ResourceNotFoundException("PaymentOption not found with name -  " + name));
        return paymentOption.get();
    }

    @Override
    public void deletePaymentOption(String paymentOptionId) {
        paymentOptionRepository.deleteById(paymentOptionId);
    }
}
