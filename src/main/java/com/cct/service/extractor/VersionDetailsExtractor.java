package com.cct.service.extractor;

import com.cct.model.dto.VersionDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class VersionDetailsExtractor implements Extractor<VersionDTO, Document> {
    @Override
    public Collection<VersionDTO> extract(Document from) {
        String idPrefix = extractIdPrefix(from.head());
        return from
                .body()
                .select("div.engine-block")
                .stream()
                .map(e -> {
                    String id = idPrefix + e.attr("id");
                    Double[] fuelConsumptionSpecs = e
                            .select("dl[title='Fuel Consumption Specs']")
                            .select("dd")
                            .stream()
                            .map(Element::text)
                            .filter(s -> s.contains("OR"))
                            .map(this::extractFuelConsumption)
                            .collect(Collectors.toList())
                            .toArray(new Double[]{});
                    return Pair.of(id, fuelConsumptionSpecs);
                })
                .filter(p -> p.getRight().length == 3)
                .map(p -> {
                    VersionDTO versionDTO = new VersionDTO();

                    versionDTO.setId(p.getLeft());

                    Double[] fuelConsumptionSpecs = p.getRight();
                    versionDTO.setCityFuelConsumption(fuelConsumptionSpecs[0]);
                    versionDTO.setHighwayFuelConsumption(fuelConsumptionSpecs[1]);
                    versionDTO.setMixedFuelConsumption(fuelConsumptionSpecs[2]);

                    return versionDTO;
                })
                .collect(Collectors.toSet());
    }

    private String extractIdPrefix(Element head) {
        String idPrefix = head
                .selectFirst("link[rel='canonical']")
                .attr("href");
        return idPrefix.substring(idPrefix.lastIndexOf('/') + 1) + "#a";
    }

    private Double extractFuelConsumption(String s) {
        return new Double(s.substring(s.indexOf("OR") + 3).replace(" L/100Km", ""));
    }
}
