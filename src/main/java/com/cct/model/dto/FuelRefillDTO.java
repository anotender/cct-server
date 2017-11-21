package com.cct.model.dto;

import lombok.Data;

@Data
public class FuelRefillDTO {
    private Long id;
    private Long carId;
    private Long fuelPriceId;
    private Double liters;
    private Double distance;
    private Double averageFuelConsumption;
    private Long date;
}
