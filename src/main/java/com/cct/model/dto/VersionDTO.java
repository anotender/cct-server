package com.cct.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

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
}
