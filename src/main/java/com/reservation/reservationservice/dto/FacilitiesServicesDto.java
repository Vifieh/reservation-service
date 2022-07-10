package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.constants.*;
import com.reservation.reservationservice.payload.*;
import com.reservation.reservationservice.payload.BreakfastPayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/21/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesServicesDto {

    private ParkingDto parkingDto;
    private BreakfastDto breakfastDto;
    private List<CustomDto> languageDto;
    private List<PropertyFacilityDto> propertyFacilityDtoList;
}


