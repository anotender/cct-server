package com.cct.service.api;

import com.cct.model.dto.VersionDTO;

import java.util.Collection;

public interface VersionAutoEvolutionService {
    Collection<VersionDTO> getCommonVersions(String versionId);

    Collection<VersionDTO> getVersionsForMakeAndModel(String makeId, String modelId);
}
