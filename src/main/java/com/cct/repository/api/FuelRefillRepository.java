package com.cct.repository.api;

import com.cct.model.FuelRefill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface FuelRefillRepository extends JpaRepository<FuelRefill, Long> {
    Optional<FuelRefill> findOneById(Long id);

    Collection<FuelRefill> findByCarId(Long id);

    @Query("select fr from FuelRefill fr where fr.car.version.id = :versionId")
    Collection<FuelRefill> findByVersionId(@Param("versionId") String versionId);
}
