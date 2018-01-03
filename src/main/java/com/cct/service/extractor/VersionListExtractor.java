package com.cct.service.extractor;

import com.cct.model.dto.VersionDTO;
import com.cct.util.AutoEvolutionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class VersionListExtractor implements Extractor<VersionDTO, Document> {

    private final String baseUrl;

    public VersionListExtractor(@Value("${autoevolution.base.url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Collection<VersionDTO> extract(Document from) {
        return from
                .body()
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

    private String extractFuel(Element e) {
        return e.select("b").text().replace(" ENGINES:", "");
    }

    private VersionDTO mapTripleToVersionDTO(Triple<String, String, Element> t) {
        VersionDTO versionDTO = new VersionDTO();

        versionDTO.setId(extractId(t.getRight()));
        versionDTO.setName(extractName(t.getRight()));
        versionDTO.setYears(t.getLeft());
        versionDTO.setFuel(t.getMiddle().replaceAll(" ", "_").trim());

        return versionDTO;
    }

    private String extractName(Element e) {
        return e.select("span").text();
    }

    private String extractId(Element e) {
        String id = e
                .select("a")
                .attr("href")
                .replace(baseUrl + "/cars/", "");
        return AutoEvolutionUtils.encodeId(id);
    }
}
