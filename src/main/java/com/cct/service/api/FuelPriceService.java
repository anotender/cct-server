package com.cct.service.api;

import com.cct.model.dto.FuelPriceDTO;

import java.util.Collection;

public interface FuelPriceService {
    Collection<FuelPriceDTO> getMostRecentFuelPricesForFuelStation(String id);

    Collection<FuelPriceDTO> getMostRecentFuelPricesForFuelStations(Collection<String> ids);

    FuelPriceDTO save(FuelPriceDTO fuelPriceDTO);
}
