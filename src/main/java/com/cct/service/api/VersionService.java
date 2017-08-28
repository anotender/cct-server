package com.cct.service.api;

import com.cct.model.dto.VersionDTO;

import java.util.Collection;

public interface VersionService {
    VersionDTO getVersion(String versionId);

    Collection<VersionDTO> getVersionsForModel(String modelId);
}
