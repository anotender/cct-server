package com.cct.service.impl;

import com.cct.model.FuelRefill;
import com.cct.model.dto.FuelRefillDTO;
import com.cct.repository.api.FuelRefillRepository;
import com.cct.service.api.FuelRefillService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class FuelRefillServiceImpl implements FuelRefillService {

    private final FuelRefillRepository fuelRefillRepository;
    private final ModelMapper modelMapper;

    public FuelRefillServiceImpl(FuelRefillRepository fuelRefillRepository, ModelMapper modelMapper) {
        this.fuelRefillRepository = fuelRefillRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<FuelRefillDTO> getFuelRefillsForCar(Long id) {
        return fuelRefillRepository
                .findByCarId(id)
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public FuelRefillDTO save(FuelRefillDTO fuelRefillDTO) {
        FuelRefill fuelRefill = modelMapper.convertToEntity(fuelRefillDTO);
        return modelMapper.convertToDTO(fuelRefillRepository.save(fuelRefill));
    }
}