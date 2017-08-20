package com.cct.repository.api;

import com.cct.model.Version;

import java.util.Collection;

public interface VersionWebRepository {
    Collection<Version> findByModelId(String modelId);
}
