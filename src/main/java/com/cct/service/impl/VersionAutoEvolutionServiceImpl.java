package com.cct.service.impl;

import com.cct.model.dto.VersionDTO;
import com.cct.service.api.VersionAutoEvolutionService;
import com.cct.service.extractor.Extractor;
import com.cct.service.extractor.VersionDetailsExtractor;
import com.cct.service.extractor.VersionListExtractor;
import com.cct.util.AutoEvolutionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class VersionAutoEvolutionServiceImpl implements VersionAutoEvolutionService {

    private final String baseUrl;
    private final Extractor<VersionDTO, Document> versionListExtractor;
    private final Extractor<VersionDTO, Document> versionDetailsExtractor;

    public VersionAutoEvolutionServiceImpl(
            @Value("${autoevolution.base.url}") String baseUrl,
            VersionListExtractor versionListExtractor,
            VersionDetailsExtractor versionDetailsExtractor) {
        this.baseUrl = baseUrl;
        this.versionListExtractor = versionListExtractor;
        this.versionDetailsExtractor = versionDetailsExtractor;
    }

    @Override
    public Collection<VersionDTO> getCommonVersions(String versionId) {
        String calledVersionId = AutoEvolutionUtils.decodeId(versionId);
        return versionDetailsExtractor.extract(getWebsite(baseUrl + "/cars/" + calledVersionId));
    }

    @Override
    public Collection<VersionDTO> getVersionsForMakeAndModel(String makeId, String modelId) {
        return versionListExtractor.extract(getWebsite(baseUrl + "/" + makeId + "/" + modelId));
    }

    private Document getWebsite(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
