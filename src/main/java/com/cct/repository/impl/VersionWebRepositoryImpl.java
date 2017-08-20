package com.cct.repository.impl;

import com.cct.model.Version;
import com.cct.repository.api.VersionWebRepository;
import com.cct.util.HttpUtils;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;

@Repository
public class VersionWebRepositoryImpl implements VersionWebRepository {

    private final HttpUtils httpUtils;
    private final String baseUrl;

    public VersionWebRepositoryImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
        this.baseUrl = "https://www.autoevolution.com";
    }

    @Override
    public Collection<Version> findByModelId(String modelId) {
        return Collections.emptySet();
    }
}
