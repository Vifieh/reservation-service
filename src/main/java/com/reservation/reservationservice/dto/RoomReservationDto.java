package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.Currency;
import com.reservation.reservationservice.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 7/2/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationDto {

    private String id;
    private Date checkIn;
    private Date checkOut;
    private double totalPrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private String specialRequest;
    private String arrivalTime;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private ReservationContactDetailsDto reservationContactDetailsDto;
    private List<RoomReservationItemDto> roomReservationItemDtoList;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReservationContactDetailsDto {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private CustomDto countryDto;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomReservationItemDto {
        private String id;
        private int numberOfRooms;
        private double price;
        private String fullGuestName;
        private String guestEmail;

        @Enumerated(EnumType.STRING)
        private Currency currency;
        private CustomDto roomDto;
    }

}
