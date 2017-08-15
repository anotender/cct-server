package com.cct.model.dto;

import lombok.Data;

@Data
public class ModelDTO {
    private String id;
    private String makeId;
    private String name;
    private String version;
    private int year;
    private double highwayFuelConsumption;
    private double cityFuelConsumption;
    private double mixedFuelConsumption;
}
