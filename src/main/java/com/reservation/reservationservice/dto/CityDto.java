package com.reservation.reservationservice.dto;

import com.reservation.reservationservice.model.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vifieh Ruth
 * on 6/20/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private String id;
    private String name;
    private List<PropertyAddressDto> propertyAddressDtoList;
    private List<FileInfo> fileInfoList;
}
