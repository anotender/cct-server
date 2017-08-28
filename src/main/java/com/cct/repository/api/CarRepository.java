package com.cct.repository.api;

import com.cct.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Collection<Car> findByUserId(Long userId);

    Optional<Car> findOneById(Long id);
}
