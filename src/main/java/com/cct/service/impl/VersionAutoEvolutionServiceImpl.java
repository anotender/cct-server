package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Version;
import com.cct.model.dto.VersionDTO;
import com.cct.repository.api.VersionRepository;
import com.cct.service.api.VersionAutoEvolutionService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.VERSION_NOT_FOUND;

@Service
public class VersionAutoEvolutionServiceImpl implements VersionAutoEvolutionService {

    private final String baseUrl = "https://www.autoevolution.com";
    private final VersionRepository versionRepository;

    public VersionAutoEvolutionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public void fetchData(String versionId) {
        String calledVersionId = decodeId(versionId);
        getWebsiteBody(baseUrl + "/cars/" + calledVersionId)
                .select("div.engine-block")
                .forEach(e -> {
                    String id = calledVersionId.substring(0, calledVersionId.indexOf("#a") + 2) + e.attr("id");
                    Version version = versionRepository
                            .findOneById(encodeId(id))
                            .orElseThrow(() -> new BadRequestException(VERSION_NOT_FOUND));

                    Double[] fuelConsumptionSpecs = e
                            .select("dl[title='Fuel Consumption Specs']")
                            .select("dd")
                            .stream()
                            .map(Element::text)
                            .filter(s -> s.contains("OR"))
                            .map(s -> s
                                    .substring(s.indexOf("OR") + 3)
                                    .replace(" L/100Km", "")
                            )
                            .map(Double::new)
                            .collect(Collectors.toList())
                            .toArray(new Double[]{});

                    if (fuelConsumptionSpecs.length == 3) {
                        version.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                        version.setHighwayFuelConsumption(fuelConsumptionSpecs[1]);
                        version.setMixedFuelConsumption(fuelConsumptionSpecs[2]);
                    }

                });
    }

    @Override
    public Collection<VersionDTO> getVersionsForMakeAndModel(String makeId, String modelId) {
        return getWebsiteBody(baseUrl + "/" + makeId + "/" + modelId)
                .select("div[itemtype='https://schema.org/Car']")
                .stream()
                .flatMap(e -> {
                    String years = e.select("p.years").text();
                    return e
                            .select("div.mot")
                            .stream()
                            .map(element -> Pair.of(years, element));
                })
                .flatMap(p -> {
                    String fuel = extractFuel(p.getRight());
                    return p
                            .getRight()
                            .select("p.engitm")
                            .stream()
                            .map(e -> Triple.of(p.getLeft(), fuel, e));
                })
                .map(this::mapTripleToVersionDTO)
                .collect(Collectors.toSet());
    }

    private String extractName(Element e) {
        return e.select("span").text();
    }

    private String extractId(Element e) {
        String id = e
                .select("a")
                .attr("href")
                .replace(baseUrl + "/cars/", "");
        return encodeId(id);
    }

    private VersionDTO mapTripleToVersionDTO(Triple<String, String, Element> t) {
        VersionDTO versionDTO = new VersionDTO();

        versionDTO.setId(extractId(t.getRight()));
        versionDTO.setName(extractName(t.getRight()));
        versionDTO.setYears(t.getLeft());
        versionDTO.setFuel(t.getMiddle().replaceAll(" ", "_").trim());

        return versionDTO;
    }

    private String extractFuel(Element e) {
        return e.select("b").text().replace(" ENGINES:", "");
    }

    private String encodeId(String id) {
        return Base64.getUrlEncoder().encodeToString(id.getBytes());
    }

    private String decodeId(String id) {
        return new String(Base64.getUrlDecoder().decode(id));
    }

    private Element getWebsiteBody(String url) {
        try {
            return Jsoup.connect(url).get().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
