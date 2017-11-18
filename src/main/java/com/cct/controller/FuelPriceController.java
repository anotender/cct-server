package com.cct.controller;

import com.cct.model.dto.FuelPriceDTO;
import com.cct.service.api.FuelPriceService;
import com.cct.util.RequestParamsUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/fuelprices")
public class FuelPriceController {

    private final FuelPriceService fuelPriceService;

    public FuelPriceController(FuelPriceService fuelPriceService) {
        this.fuelPriceService = fuelPriceService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FuelPriceDTO>> getFuelPrices(@RequestParam(value = "fuelStationId") String fuelStationId) {
        return ResponseEntity.ok(fuelPriceService.getMostRecentFuelPricesForFuelStations(RequestParamsUtils.extractStringIdsFromParam(fuelStationId)));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FuelPriceDTO> saveFuelPrice(@RequestBody FuelPriceDTO fuelPriceDTO) {
        FuelPriceDTO savedFuelPriceDTO = fuelPriceService.save(fuelPriceDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFuelPriceDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedFuelPriceDTO);
    }

}
