package com.cct.repository.api;

import com.cct.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface VersionRepository extends JpaRepository<Version, String> {
    Collection<Version> findByModelId(String modelId);
}
