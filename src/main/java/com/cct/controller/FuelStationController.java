package com.cct.controller;

import com.cct.model.dto.FuelStationDTO;
import com.cct.service.api.FuelStationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/fuelstations")
public class FuelStationController {

    private final FuelStationService fuelStationService;

    public FuelStationController(FuelStationService fuelStationService) {
        this.fuelStationService = fuelStationService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelStationDTO> getFuelStation(@PathVariable("id") String id) {
        return ResponseEntity.ok(fuelStationService.getFuelStation(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FuelStationDTO>> getFuelStationsInArea(
            @RequestParam("radius") Integer radius,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude
    ) {
        return ResponseEntity.ok(fuelStationService.getFuelStationsInArea(radius, latitude, longitude));
    }
}
