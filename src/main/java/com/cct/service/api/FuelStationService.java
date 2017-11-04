package com.cct.service.api;

import com.cct.model.dto.FuelStationDTO;

import java.util.Collection;

public interface FuelStationService {
    FuelStationDTO getFuelStation(String id);

    Collection<FuelStationDTO> getFuelStationsInArea(int radius, double latitude, double longitude);
}
