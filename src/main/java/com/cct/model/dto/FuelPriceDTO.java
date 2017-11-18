package com.cct.model.dto;

import lombok.Data;

@Data
public class FuelPriceDTO {
    private Long id;
    private String fuel;
    private Double price;
    private Long date;
    private String fuelStationId;
}
