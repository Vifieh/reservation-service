package com.reservation.reservationservice.service;

import com.reservation.reservationservice.model.PaymentResponse;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 7/7/22
 */
public interface TransactionService {

    void handlePaymentResponse(String ref, String zitopay_transaction_reference);

    PaymentResponse getPaymentReference(Long id);

    List<PaymentResponse> getAllPaymentReferences();
}
