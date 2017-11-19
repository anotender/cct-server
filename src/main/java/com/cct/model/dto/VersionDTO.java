package com.cct.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class VersionDTO {
    private String id;
    private String modelId;
    private String name;
    private String years;
    private String fuel;
    private Double cityFuelConsumption;
    private Double highwayFuelConsumption;
    private Double mixedFuelConsumption;
    private Double averageFuelConsumption;
    private Set<Long> cars = new HashSet<>();
    private Set<Long> ratings = new HashSet<>();
}
