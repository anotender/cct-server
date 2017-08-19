package com.cct.service.api;

import com.cct.model.dto.CarDTO;

import java.util.Collection;

public interface CarService {
    CarDTO getCar(Long id);

    Collection<CarDTO> getCarsForUser(Long userId);
}
