package com.cct.repository.api;

import com.cct.model.FuelRefill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FuelRefillRepository extends JpaRepository<FuelRefill, Long> {
    Collection<FuelRefill> findByCarId(Long id);
}
