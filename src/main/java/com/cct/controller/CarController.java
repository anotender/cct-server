package com.cct.controller;

import com.cct.model.dto.CarDTO;
import com.cct.model.dto.FuelRefillDTO;
import com.cct.service.api.CarService;
import com.cct.service.api.FuelRefillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final FuelRefillService fuelRefillService;

    public CarController(CarService carService, FuelRefillService fuelRefillService) {
        this.carService = carService;
        this.fuelRefillService = fuelRefillService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDTO> getCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(carService.getCar(id));
    }

    @GetMapping(value = "/{id}/fuelrefills", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FuelRefillDTO>> getFuelRefillsForCar(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fuelRefillService.getFuelRefillsForCar(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDTO> saveCar(@RequestBody CarDTO carDTO) {
        CarDTO savedCarDTO = carService.save(carDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCarDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCarDTO);
    }


}
