package com.cct.repository.api;

import com.cct.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface VersionRepository extends JpaRepository<Version, String> {
    Optional<Version> findOneById(String id);

    Collection<Version> findByModelId(String modelId);
}
