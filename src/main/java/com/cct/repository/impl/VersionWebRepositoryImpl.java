package com.cct.repository.impl;

import com.cct.model.Version;
import com.cct.repository.api.VersionWebRepository;
import com.cct.util.HttpUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VersionWebRepositoryImpl implements VersionWebRepository {

    private final HttpUtils httpUtils;
    private final String baseUrl;
    private final List<String> fuelTypes = Arrays.asList("Diesel", "Gasoline", "Hybrid", "Ethanol");

    public VersionWebRepositoryImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
        this.baseUrl = "https://www.autoevolution.com";
    }

    @Override
    public void fetchData(Version v) {
        httpUtils
                .getWebsiteBody(baseUrl + "/cars/" + v.getId())
                .ifPresent(e -> {
                    String fuel = e
                            .select("dl[title='General Specs']")
                            .select("dt")
                            .stream()
                            .map(Element::text)
                            .filter(fuelTypes::contains)
                            .map(String::trim)
                            .findFirst()
                            .orElseThrow(RuntimeException::new);

                    Double[] fuelConsumptionSpecs = e
                            .select("d1[title='Fuel Consumption Specs']")
                            .select("dd")
                            .stream()
                            .map(Element::text)
                            .map(s -> s.substring(s.indexOf("OR") + 3))
                            .map(s -> s.replace(" L/100Km", ""))
                            .map(Double::new)
                            .collect(Collectors.toList())
                            .toArray(new Double[]{});

                    v.setFuel(fuel);
                    v.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                    v.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                    v.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                });
    }

    @Override
    public Collection<Version> findByMakeIdAndModelId(String makeId, String modelId) {
        return httpUtils
                .getWebsiteBody(baseUrl + "/" + makeId + "/" + modelId)
                .orElseThrow(RuntimeException::new)
                .select("div[itemtype='https://schema.org/Car']")
                .stream()
                .map(e -> {
                    Version version = new Version();

                    String id = e
                            .select("p.engitm > a")
                            .attr("href")
                            .replace(baseUrl + "/cars/", "");
                    version.setId(id);

                    String years = e.select("p.years").text();
                    version.setYears(years);

                    String name = e.select("p.engitm > a > span").text();
                    version.setName(name);

                    return version;
                })
                .collect(Collectors.toSet());
    }
}
