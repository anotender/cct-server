package com.cct.service.api;

import com.cct.model.dto.VersionDTO;

import java.util.Collection;

public interface VersionService {
    Collection<VersionDTO> getVersionsForModel(String modelId);
}
