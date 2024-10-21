package com.techzo.cambiazo.exchanges.domain.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private Long districtId;
    private String districtName;
    private Long departmentId;
    private String departmentName;
    private Long countryId;
    private String countryName;
}
