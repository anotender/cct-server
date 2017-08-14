package com.cct.repository.api;

import com.cct.model.Make;

import java.util.Collection;
import java.util.Optional;

public interface MakeRepository {
    Optional<Make> findOneById(String id);

    Collection<Make> findAll();
}
