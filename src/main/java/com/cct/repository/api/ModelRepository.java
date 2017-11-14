package com.cct.repository.api;

import com.cct.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, String> {
    Optional<Model> findOneById(String id);

    Collection<Model> findByMakeIdOrderByName(String id);

    Collection<Model> findByIdIn(Collection<String> ids);
}
