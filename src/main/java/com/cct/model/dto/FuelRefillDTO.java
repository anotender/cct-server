package com.cct.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FuelRefillDTO {
    private Long id;
    private Long carId;
    private String fuelStationId;
    private Double liters;
    private LocalDateTime date;
    private Double distance;
}
