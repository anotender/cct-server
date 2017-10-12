package com.cct.service.api;

import com.cct.model.dto.VersionDTO;

import java.util.Collection;

public interface VersionService {
    VersionDTO getVersion(String versionId);

    Collection<VersionDTO> getVersions();

    Collection<VersionDTO> getVersionsOrderByPopularity();

    Collection<VersionDTO> getVersionsForModel(String modelId);
}
