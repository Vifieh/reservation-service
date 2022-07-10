package com.reservation.reservationservice.service.serviceImpl;

import com.reservation.reservationservice.exception.ResourceAlreadyExistException;
import com.reservation.reservationservice.exception.ResourceNotFoundException;
import com.reservation.reservationservice.model.PaymentResponse;
import com.reservation.reservationservice.model.RoomReservation;
import com.reservation.reservationservice.repository.RoomReservationRepository;
import com.reservation.reservationservice.repository.TransactionRepository;
import com.reservation.reservationservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Vifieh Ruth
 * on 7/7/22
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    RoomReservationRepository roomReservationRepository;

    @Override
    public void handlePaymentResponse(String ref, String zitopay_transaction_reference) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setRef(ref);
        paymentResponse.setZitopay_transaction_reference(zitopay_transaction_reference);


        Optional<PaymentResponse> paymentResponseOptional = transactionRepository.findByRef(ref);
        if (paymentResponseOptional.isPresent()) {
            throw new ResourceAlreadyExistException("Transaction ref already exist");
        }
        transactionRepository.save(paymentResponse);

        Optional<RoomReservation> roomReservationOptional = roomReservationRepository.findByRef(ref);
        if (!roomReservationOptional.isPresent()) {
            throw new ResourceNotFoundException("Ref not found");
        }
        roomReservationOptional.get().setHasPayed(true);
        roomReservationRepository.save(roomReservationOptional.get());
    }

    @Override
    public PaymentResponse getPaymentReference(Long id) {
        Optional<PaymentResponse> paymentResponseOptional = transactionRepository.findById(id);
        paymentResponseOptional.orElseThrow(() -> new ResourceNotFoundException("payment not found with id - "+ id));
        return paymentResponseOptional.get();
    }

    @Override
    public List<PaymentResponse> getAllPaymentReferences() {
        return transactionRepository.findAll();
    }
}
