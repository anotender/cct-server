package com.cct.model.dto;

import lombok.Data;

@Data
public class FuelRefillDTO {
    private Long id;
    private Long carId;
    private Double liters;
    private Long date;
    private Double distance;
}
