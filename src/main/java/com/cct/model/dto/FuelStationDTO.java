package com.cct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelStationDTO {
    private String id;
    private Double latitude;
    private Double longitude;
    private String name;
    private String address;
    private Set<Long> fuelRefills = new HashSet<>();
}
