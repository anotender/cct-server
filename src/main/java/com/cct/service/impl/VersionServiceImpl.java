package com.cct.service.impl;

import com.cct.model.dto.VersionDTO;
import com.cct.repository.api.VersionRepository;
import com.cct.repository.api.VersionWebRepository;
import com.cct.service.api.VersionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;
    private final VersionWebRepository versionWebRepository;

    public VersionServiceImpl(VersionRepository versionRepository, VersionWebRepository versionWebRepository) {
        this.versionRepository = versionRepository;
        this.versionWebRepository = versionWebRepository;
    }

    @Override
    public Collection<VersionDTO> getVersionsForModel(String modelId) {
        return Collections.emptySet();
    }
}
