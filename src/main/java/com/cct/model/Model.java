package com.cct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    private String id;
    private String makeId;
    private String name;
    private String version;
    private int year;
    private double highwayFuelConsumption;
    private double cityFuelConsumption;
    private double mixedFuelConsumption;
}
