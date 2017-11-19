package com.cct.service.impl;

import com.cct.model.dto.FuelStationDTO;
import com.cct.service.api.FuelStationGoogleService;
import com.cct.service.api.FuelStationService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FuelStationServiceImpl implements FuelStationService {

    private final FuelStationGoogleService fuelStationGoogleService;

    public FuelStationServiceImpl(FuelStationGoogleService fuelStationGoogleService) {
        this.fuelStationGoogleService = fuelStationGoogleService;
    }

    @Override
    public FuelStationDTO getFuelStation(String id) {
        return fuelStationGoogleService.getFuelStation(id);
    }

    @Override
    public Collection<FuelStationDTO> getFuelStationsInArea(int radius, double latitude, double longitude) {
        return fuelStationGoogleService.getFuelStationsInArea(radius, latitude, longitude);
    }
}
