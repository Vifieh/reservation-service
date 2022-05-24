package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.PaymentOption;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 5/24/22
 */

public interface PaymentOptionService {

    void createPaymentOption(PaymentOption paymentOption);

    void editPaymentOption(String paymentOptionId, PaymentOption paymentOption);

    List<PaymentOption> getAllPaymentOptions();

    PaymentOption getPaymentOption(String paymentOptionId);

    PaymentOption getPaymentOptionByName(String name);

    void deletePaymentOption(String paymentOptionId);
}
