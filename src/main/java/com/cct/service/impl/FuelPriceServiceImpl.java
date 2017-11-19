package com.cct.service.impl;

import com.cct.model.FuelPrice;
import com.cct.model.dto.FuelPriceDTO;
import com.cct.repository.api.FuelPriceRepository;
import com.cct.service.api.FuelPriceService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FuelPriceServiceImpl implements FuelPriceService {

    private final FuelPriceRepository fuelPriceRepository;
    private final ModelMapper modelMapper;

    public FuelPriceServiceImpl(FuelPriceRepository fuelPriceRepository, ModelMapper modelMapper) {
        this.fuelPriceRepository = fuelPriceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<FuelPriceDTO> getMostRecentFuelPricesForFuelStation(String id) {
        return fuelPriceRepository
                .findByFuelStationIdOrderByDateDesc(id)
                .stream()
                .collect(Collectors.groupingBy(FuelPrice::getFuel))
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(this::findMostRecentFuelPrice)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<FuelPriceDTO> getMostRecentFuelPricesForFuelStations(Collection<String> ids) {
        return ids
                .stream()
                .flatMap(id -> getMostRecentFuelPricesForFuelStation(id).stream())
                .collect(Collectors.toSet());
    }

    @Override
    public FuelPriceDTO save(FuelPriceDTO fuelPriceDTO) {
        FuelPrice savedFuelPrice = fuelPriceRepository.save(modelMapper.convertToEntity(fuelPriceDTO));
        return modelMapper.convertToDTO(savedFuelPrice);
    }

    private Optional<FuelPrice> findMostRecentFuelPrice(List<FuelPrice> fuelPrices) {
        return fuelPrices
                .stream()
                .sorted(Comparator.comparing(FuelPrice::getDate).reversed())
                .findFirst();
    }

}
