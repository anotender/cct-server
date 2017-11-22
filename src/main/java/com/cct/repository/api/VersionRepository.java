package com.cct.repository.api;

import com.cct.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, String> {
    Optional<Version> findOneById(String id);

    @Query("select c.version from Car c where c.id = :carId")
    Optional<Version> findOneByCarId(@Param("carId") Long carId);

    @Query("select fr.car.version from FuelRefill fr where fr.id = :fuelRefillId")
    Optional<Version> findOneByFuelRefillId(@Param("fuelRefillId") Long fuelRefillId);

    @Query("select v from Version v where v.model.id = :modelId order by v.name asc")
    Collection<Version> findByModelId(@Param("modelId") String modelId);
}
