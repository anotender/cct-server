package com.cct.service.impl;

import com.cct.model.FuelRefill;
import com.cct.model.dto.FuelStationDTO;
import com.cct.repository.api.FuelRefillRepository;
import com.cct.service.api.FuelStationGoogleService;
import com.cct.service.api.FuelStationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FuelStationServiceImpl implements FuelStationService {

    private final FuelStationGoogleService fuelStationGoogleService;
    private final FuelRefillRepository fuelRefillRepository;

    public FuelStationServiceImpl(FuelStationGoogleService fuelStationGoogleService, FuelRefillRepository fuelRefillRepository) {
        this.fuelStationGoogleService = fuelStationGoogleService;
        this.fuelRefillRepository = fuelRefillRepository;
    }

    @Override
    public FuelStationDTO getFuelStation(String id) {
        return fetchFuelRefills(fuelStationGoogleService.getFuelStation(id));
    }

    @Override
    public Collection<FuelStationDTO> getFuelStationsInArea(int radius, double latitude, double longitude) {
        return fuelStationGoogleService
                .getFuelStationsInArea(radius, latitude, longitude)
                .stream()
                .map(this::fetchFuelRefills)
                .collect(Collectors.toSet());
    }

    /**
     * Takes as a parameter fuelStation from Google Places API and fetches data from database.
     *
     * @param fuelStationDTO FuelStationDTO object from Google Places API
     * @return fuelStationDTO with fetched fuel refills
     */
    private FuelStationDTO fetchFuelRefills(FuelStationDTO fuelStationDTO) {
        Set<Long> fuelRefills = fuelRefillRepository
                .findByFuelStationId(fuelStationDTO.getId())
                .stream()
                .map(FuelRefill::getId)
                .collect(Collectors.toSet());
        fuelStationDTO.setFuelRefills(fuelRefills);
        return fuelStationDTO;
    }
}
