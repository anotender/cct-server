package com.cct.service.api;

import com.cct.model.dto.VersionDTO;

import java.util.Collection;

public interface VersionService {
    VersionDTO getVersion(String versionId);

    Collection<VersionDTO> getVersions();

    Collection<VersionDTO> getVersions(Integer limit);

    Collection<VersionDTO> getVersions(Boolean orderByPopularity);

    Collection<VersionDTO> getVersions(Integer limit, Boolean orderByPopularity);

    Collection<VersionDTO> getVersionsForModel(String modelId);
}
