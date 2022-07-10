package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.model.PaymentResponse;
import com.reservation.reservationservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Vifieh Ruth
 * on 7/7/22
 */

@RequestMapping("api/v1/public/")
@CrossOrigin
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    ResponseEntity<?> notification(@RequestParam String ref, @RequestParam String zitopay_transaction_reference) {
        transactionService.handlePaymentResponse(ref, zitopay_transaction_reference);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    ResponseEntity<PaymentResponse> getPaymentReference(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getPaymentReference(id));
    }

    @GetMapping("all")
    ResponseEntity<List<PaymentResponse>> getAllPaymentReferences() {
        return ResponseEntity.ok(transactionService.getAllPaymentReferences());
    }
 }
