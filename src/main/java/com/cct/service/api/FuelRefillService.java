package com.cct.service.api;

import com.cct.model.dto.FuelRefillDTO;

import java.util.Collection;

public interface FuelRefillService {
    FuelRefillDTO getFuelRefill(Long id);

    Collection<FuelRefillDTO> getFuelRefillsForCar(Long id);

    FuelRefillDTO save(FuelRefillDTO fuelRefillDTO);

    void delete(Long id);
}
