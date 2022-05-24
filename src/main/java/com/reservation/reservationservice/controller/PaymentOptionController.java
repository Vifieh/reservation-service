package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.PaymentOption;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.PaymentOptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1/protected/")
@CrossOrigin
@RestController
public class PaymentOptionController {

    @Autowired
    PaymentOptionService paymentOptionService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("paymentOptions")
    public ResponseEntity<ResponseMessage> createPaymentOption(@RequestBody CustomPayload paymentOptionPayload) {
        PaymentOption paymentOption = modelMapper.map(paymentOptionPayload, PaymentOption.class);
        paymentOptionService.createPaymentOption(paymentOption);
        message = "PaymentOption created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("paymentOptions/{paymentOptionId}")
    public ResponseEntity<ResponseMessage> editPaymentOption(@PathVariable String paymentOptionId,
                                                            @RequestBody CustomPayload paymentOptionPayload) {
        PaymentOption paymentOption = modelMapper.map(paymentOptionPayload, PaymentOption.class);
        paymentOptionService.editPaymentOption(paymentOptionId, paymentOption);
        message = "PaymentOption edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("paymentOptions")
    public ResponseEntity<List<CustomDto>> getPaymentOptions() {
        List<PaymentOption> paymentOptions = paymentOptionService.getAllPaymentOptions();
        List<CustomDto> paymentOptionsDtos = paymentOptions.stream().map(paymentOption ->
                modelMapper.map(paymentOption, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(paymentOptionsDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("paymentOptions/{paymentOptionId}")
    public ResponseEntity<CustomDto> getPaymentOption(@PathVariable String paymentOptionId) {
        PaymentOption paymentOption = paymentOptionService.getPaymentOption(paymentOptionId);
        CustomDto paymentOptionsDto = modelMapper.map(paymentOption, CustomDto.class);
        return new ResponseEntity<>(paymentOptionsDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("paymentOptions/{paymentOptionId}")
    public ResponseEntity<ResponseMessage> deletePaymentOption(@PathVariable String paymentOptionId) {
        paymentOptionService.deletePaymentOption(paymentOptionId);
        message = "PaymentOption deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }
}
