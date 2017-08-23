package com.cct.repository.impl;

import com.cct.model.Version;
import com.cct.repository.api.VersionWebRepository;
import com.cct.util.HttpUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class VersionWebRepositoryImpl implements VersionWebRepository {

    private final HttpUtils httpUtils;
    private final String baseUrl;

    public VersionWebRepositoryImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
        this.baseUrl = "https://www.autoevolution.com";
    }

    @Override
    public void fetchData(Version v) {
        httpUtils
                .getWebsiteBody(baseUrl + "/cars/" + new String(Base64.getUrlDecoder().decode(v.getId())))
                .ifPresent(e -> {
                    Double[] fuelConsumptionSpecs = e
                            .select("dl[title='Fuel Consumption Specs']")
                            .select("dd")
                            .stream()
                            .map(Element::text)
                            .map(s -> s.substring(s.indexOf("OR") + 3))
                            .map(s -> s.replace(" L/100Km", ""))
                            .map(Double::new)
                            .collect(Collectors.toList())
                            .toArray(new Double[]{});

                    v.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                    v.setHighwayFuelConsumption(fuelConsumptionSpecs[1]);
                    v.setMixedFuelConsumption(fuelConsumptionSpecs[2]);
                });
    }

    @Override
    public Collection<Version> findByMakeIdAndModelId(String makeId, String modelId) {
        return httpUtils
                .getWebsiteBody(baseUrl + "/" + makeId + "/" + modelId)
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
                    String fuel = p
                            .getRight()
                            .select("b")
                            .text()
                            .replace(" ENGINES:", "");
                    return p
                            .getRight()
                            .select("p.engitm")
                            .stream()
                            .map(e -> Triple.of(p.getLeft(), fuel, e));
                })
                .map(t -> {
                    String name = t
                            .getRight()
                            .select("span")
                            .text();

                    String id = Base64
                            .getUrlEncoder()
                            .encodeToString(t
                                    .getRight()
                                    .select("a")
                                    .attr("href")
                                    .replace(baseUrl + "/cars/", "")
                                    .getBytes()
                            );

                    Version version = new Version();

                    version.setId(id);
                    version.setName(name);
                    version.setYears(t.getLeft());
                    version.setFuel(t.getMiddle());

                    return version;
                })
                .collect(Collectors.toSet());
    }
}