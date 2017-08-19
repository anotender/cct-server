package com.cct.repository.api;

import com.cct.model.Model;

import java.util.Collection;
import java.util.Optional;

public interface ModelWebRepository {
    Optional<Model> findOneById(String id);

    Collection<Model> findByMakeId(String makeId);
}
