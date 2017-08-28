package com.cct.repository.api;

import com.cct.model.Version;

import java.util.Collection;

public interface VersionWebRepository {
    void fetchData(Version v);

    Collection<Version> findByMakeIdAndModelId(String makeId, String modelId);
}
