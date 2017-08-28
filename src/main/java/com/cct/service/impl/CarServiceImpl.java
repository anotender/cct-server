package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Car;
import com.cct.model.dto.CarDTO;
import com.cct.repository.api.CarRepository;
import com.cct.service.api.CarService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.CAR_NOT_FOUND;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarDTO getCar(Long id) {
        return carRepository
                .findOneById(id)
                .map(modelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(CAR_NOT_FOUND));
    }

    @Override
    public Collection<CarDTO> getCarsForUser(Long userId) {
        return carRepository
                .findByUserId(userId)
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public CarDTO save(CarDTO carDTO) {
        Car car = modelMapper.convertToEntity(carDTO);
        return modelMapper.convertToDTO(carRepository.save(car));
    }
}
