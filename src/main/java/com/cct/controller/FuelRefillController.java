package com.cct.controller;

import com.cct.model.dto.FuelRefillDTO;
import com.cct.service.api.FuelRefillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/fuelrefills")
public class FuelRefillController {

    private final FuelRefillService fuelRefillService;

    public FuelRefillController(FuelRefillService fuelRefillService) {
        this.fuelRefillService = fuelRefillService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelRefillDTO> getFuelRefillForCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fuelRefillService.getFuelRefill(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelRefillDTO> saveFuelRefillForCar(@RequestBody FuelRefillDTO fuelRefillDTO) {
        FuelRefillDTO savedFuelRefillDTO = fuelRefillService.save(fuelRefillDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFuelRefillDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedFuelRefillDTO);
    }
}
