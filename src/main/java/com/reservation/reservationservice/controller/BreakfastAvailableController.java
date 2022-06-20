package com.reservation.reservationservice.controller;

import com.reservation.reservationservice.dto.CustomDto;
import com.reservation.reservationservice.dto.ResponseMessage;
import com.reservation.reservationservice.model.BreakfastAvailable;
import com.reservation.reservationservice.payload.CustomPayload;
import com.reservation.reservationservice.service.BreakfastAvailableService;
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
public class BreakfastAvailableController {

    @Autowired
    BreakfastAvailableService breakfastAvailableService;

    @Autowired
    ModelMapper modelMapper;

    String message = null;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("breakfastAvailable")
    public ResponseEntity<ResponseMessage> createBreakfastAvailable(@RequestBody CustomPayload breakfastAvailablePayload) {
        BreakfastAvailable breakfastAvailable = getBreakfastAvailable(breakfastAvailablePayload);
        breakfastAvailableService.createBreakfastAvailable(breakfastAvailable);
        message = "Breakfast Available created successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("breakfastAvailable/{breakfastAvailableId}")
    public ResponseEntity<ResponseMessage> editBreakfastAvailable(@PathVariable String breakfastAvailableId,
                                                            @RequestBody CustomPayload breakfastAvailablePayload) {
        BreakfastAvailable breakfastAvailable = getBreakfastAvailable(breakfastAvailablePayload);
        breakfastAvailableService.editBreakfastAvailable(breakfastAvailableId, breakfastAvailable);
        message = "Breakfast Available edited successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("breakfastsAvailable")
    public ResponseEntity<List<CustomDto>> getAllBreakfastAvailable() {
        List<BreakfastAvailable> breakfastAvailableList = breakfastAvailableService.getAllBreakfastAvailable();
        List<CustomDto> breakfastAvailableDtos = breakfastAvailableList.stream().map(breakfastAvailable ->
                modelMapper.map(breakfastAvailable, CustomDto.class)).collect(Collectors.toList());
        return new ResponseEntity<>(breakfastAvailableDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    @GetMapping("breakfastAvailable/{breakfastAvailableId}")
    public ResponseEntity<CustomDto> getBreakfastAvailable(@PathVariable String breakfastAvailableId) {
        BreakfastAvailable breakfastAvailable = breakfastAvailableService.getBreakfastAvailable(breakfastAvailableId);
        CustomDto breakfastAvailableDto = modelMapper.map(breakfastAvailable, CustomDto.class);
        return new ResponseEntity<>(breakfastAvailableDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("breakfastAvailable/{breakfastAvailableId}")
    public ResponseEntity<ResponseMessage> deleteBreakfastAvailable(@PathVariable String breakfastAvailableId) {
        breakfastAvailableService.deleteBreakfastAvailable(breakfastAvailableId);
        message = "Breakfast Available deleted successfully!";
        return new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
    }

    private BreakfastAvailable getBreakfastAvailable(CustomPayload breakfastAvailablePayload) {
        BreakfastAvailable breakfastAvailable = modelMapper.map(breakfastAvailablePayload, BreakfastAvailable.class);
        return breakfastAvailable;
    }
}
