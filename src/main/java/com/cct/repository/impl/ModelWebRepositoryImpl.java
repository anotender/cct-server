package com.cct.repository.impl;

import com.cct.model.Make;
import com.cct.model.Model;
import com.cct.repository.api.ModelWebRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ModelWebRepositoryImpl implements ModelWebRepository {

    private final String baseUrl = "https://www.autoevolution.com";

    @Override
    public Optional<Model> findOneById(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<Model> findByMakeId(String makeId) {
        Element bodyElement;
        try {
            bodyElement = Jsoup
                    .connect(baseUrl + "/" + makeId)
                    .get()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String makeName = bodyElement.select("b[itemprop='name']").text();

        return bodyElement
                .select("div.carmod")
                .stream()
                .map(e -> prepareModel(e, makeId, makeName))
                .collect(Collectors.toSet());
    }

    private Model prepareModel(Element e, String makeId, String makeName) {
        String name = e.select("h4").text().replace(makeName, "");
        String body = e.select("p.body").text();
        String id = e
                .select("a")
                .attr("href")
                .replace(baseUrl, "")
                .replace(makeId, "")
                .replace("/", "");

        Model model = new Model();

        model.setId(id);
        model.setName(name);
        model.setBody(body);

        Make make = new Make();
        make.setId(makeId);
        make.setName(makeName);
        model.setMake(make);

        return model;
    }
}
