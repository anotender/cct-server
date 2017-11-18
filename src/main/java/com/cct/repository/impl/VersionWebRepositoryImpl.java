package com.cct.repository.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Fuel;
import com.cct.model.Version;
import com.cct.repository.api.VersionRepository;
import com.cct.repository.api.VersionWebRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.VERSION_NOT_FOUND;
import static com.cct.model.Fuel.*;

@Repository
public class VersionWebRepositoryImpl implements VersionWebRepository {

    private final String baseUrl = "https://www.autoevolution.com";
    private final VersionRepository versionRepository;
    private Map<String, Fuel> stringFuelMap = new HashMap<>();

    public VersionWebRepositoryImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @PostConstruct
    public void init() {
        stringFuelMap.put("GASOLINE", GASOLINE);
        stringFuelMap.put("DIESEL", DIESEL);
        stringFuelMap.put("NATURAL GAS", NATURAL_GAS);
        stringFuelMap.put("HYBRID", HYBRID);
        stringFuelMap.put("ETHANOL", ETHANOL);
        stringFuelMap.put("ELECTRIC", ELECTRIC);
    }

    @Override
    public void fetchData(Version v) {
        String calledVersionId = decodeId(v.getId());
        getWebsiteBody(baseUrl + "/cars/" + calledVersionId)
                .map(e -> e.select("div.engine-block"))
                .ifPresent(elements -> elements.forEach(e -> {
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
                            .map(s -> s.substring(s.indexOf("OR") + 3))
                            .map(s -> s.replace(" L/100Km", ""))
                            .map(Double::new)
                            .collect(Collectors.toList())
                            .toArray(new Double[]{});

                    if (fuelConsumptionSpecs.length == 3) {
                        version.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                        version.setHighwayFuelConsumption(fuelConsumptionSpecs[1]);
                        version.setMixedFuelConsumption(fuelConsumptionSpecs[2]);
                    }
                }));
    }

    @Override
    public Collection<Version> findByMakeIdAndModelId(String makeId, String modelId) {
        return getWebsiteBody(baseUrl + "/" + makeId + "/" + modelId)
                .orElseThrow(RuntimeException::new)
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
                .map(t -> {
                    Version version = new Version();

                    version.setId(extractId(t.getRight()));
                    version.setName(extractName(t.getRight()));
                    version.setYears(t.getLeft());
                    version.setFuel(resolveFuel(t.getMiddle()));

                    return version;
                })
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

    private String extractFuel(Element e) {
        return e.select("b").text().replace(" ENGINES:", "");
    }

    private String encodeId(String id) {
        return Base64.getUrlEncoder().encodeToString(id.getBytes());
    }

    private String decodeId(String id) {
        return new String(Base64.getUrlDecoder().decode(id));
    }

    private Optional<Element> getWebsiteBody(String url) {
        try {
            return Optional.of(Jsoup.connect(url).get().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Fuel resolveFuel(String fuel) {
        return stringFuelMap.getOrDefault(fuel, UNKNOWN);
    }
}
