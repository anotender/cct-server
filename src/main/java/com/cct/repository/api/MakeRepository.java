package com.cct.repository.api;

import com.cct.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MakeRepository extends JpaRepository<Make, String> {
    Optional<Make> findOneById(String id);
}
