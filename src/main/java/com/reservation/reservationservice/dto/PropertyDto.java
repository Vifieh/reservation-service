package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.BaseEntity;
import com.reservation.reservationservice.model.City;
import com.reservation.reservationservice.model.Country;
import com.reservation.reservationservice.model.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto extends BaseEntity {

    private String id;
    private String name;
    private int rating;
//    private int reviews;
    private boolean pending;
    private boolean completedRegistration;
    private PropertyContactDetailsDto propertyContactDetailsDto;
    private PropertyAddressDto propertyAddressDto;
    private List<FileInfo> fileInfoList;
//    private FacilitiesServicesDto facilitiesServicesDto;
//    private RoomDto roomDto;
}

