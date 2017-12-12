package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.FuelRefill;
import com.cct.model.Version;
import com.cct.model.dto.FuelRefillDTO;
import com.cct.repository.api.FuelRefillRepository;
import com.cct.repository.api.VersionRepository;
import com.cct.service.api.FuelRefillService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.FUEL_REFILL_NOT_FOUND;
import static com.cct.util.FuelRefillUtils.countAverageFuelConsumption;

@Service
@Transactional
public class FuelRefillServiceImpl implements FuelRefillService {

    private final FuelRefillRepository fuelRefillRepository;
    private final VersionRepository versionRepository;

    public FuelRefillServiceImpl(FuelRefillRepository fuelRefillRepository, VersionRepository versionRepository) {
        this.fuelRefillRepository = fuelRefillRepository;
        this.versionRepository = versionRepository;
    }

    @Override
    public FuelRefillDTO getFuelRefill(Long id) {
        return fuelRefillRepository
                .findOneById(id)
                .map(ModelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(FUEL_REFILL_NOT_FOUND));
    }

    @Override
    public Collection<FuelRefillDTO> getFuelRefillsForCar(Long id) {
        return fuelRefillRepository
                .findByCarId(id)
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public FuelRefillDTO save(FuelRefillDTO fuelRefillDTO) {
        FuelRefill fuelRefill = ModelMapper.convertToEntity(fuelRefillDTO);
        fuelRefill.setAverageFuelConsumption(countAverageFuelConsumption(fuelRefill));
        FuelRefill savedFuelRefill = fuelRefillRepository.save(fuelRefill);

        versionRepository
                .findOneByCarId(savedFuelRefill.getCar().getId())
                .ifPresent(this::updateVersionAverageFuelConsumption);

        return ModelMapper.convertToDTO(savedFuelRefill);
    }

    @Override
    public void delete(Long id) {
        Optional<Version> version = versionRepository.findOneByFuelRefillId(id);
        fuelRefillRepository.delete(id);
        version.ifPresent(this::updateVersionAverageFuelConsumption);
    }

    private void updateVersionAverageFuelConsumption(Version v) {
        Collection<FuelRefill> fuelRefills = fuelRefillRepository.findByVersionId(v.getId());
        v.setAverageFuelConsumption(countAverageFuelConsumption(fuelRefills));
    }

}
