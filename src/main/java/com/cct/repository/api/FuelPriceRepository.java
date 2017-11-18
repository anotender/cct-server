package com.cct.repository.api;

import com.cct.model.FuelPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FuelPriceRepository extends JpaRepository<FuelPrice, Long> {
    Collection<FuelPrice> findByFuelStationIdOrderByDateDesc(String fuelStationId);
}
