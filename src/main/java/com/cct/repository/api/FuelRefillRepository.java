package com.cct.repository.api;

import com.cct.model.FuelRefill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface FuelRefillRepository extends JpaRepository<FuelRefill, Long> {
    Optional<FuelRefill> findOneById(Long id);

    Collection<FuelRefill> findByCarId(Long id);

    Collection<FuelRefill> findByFuelStationId(String id);
}
