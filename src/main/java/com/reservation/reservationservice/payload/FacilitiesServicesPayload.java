package com.reservation.reservationservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/11/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesServicesPayload {

    private ParkingPayload parkingPayload;
    private BreakfastPayload breakfastPayload;
    private List<CustomPayload> languagePayload;
    private List<PropertyFacilityPayload> propertyFacilityPayloadList;
}
