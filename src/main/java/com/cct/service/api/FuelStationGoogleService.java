package com.cct.service.api;

import com.cct.model.dto.FuelStationDTO;

import java.util.Collection;

public interface FuelStationGoogleService {
    Collection<FuelStationDTO> getFuelStationsInArea(int radius, double latitude, double longitude);

    FuelStationDTO getFuelStation(String id);
}
