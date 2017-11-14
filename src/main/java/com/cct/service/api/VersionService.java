package com.cct.service.api;

import com.cct.model.dto.VersionDTO;

import java.util.Collection;

public interface VersionService {
    VersionDTO getVersion(String versionId);

    Collection<VersionDTO> getVersions();

    Collection<VersionDTO> getVersions(Collection<String> ids);

    Collection<VersionDTO> getVersionsOrderByPopularity();

    Collection<VersionDTO> getVersionsOrderByPopularity(Collection<VersionDTO> versions);

    Collection<VersionDTO> getVersionsForModel(String modelId);
}
