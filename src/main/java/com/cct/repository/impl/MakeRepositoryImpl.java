package com.cct.repository.impl;

import com.cct.model.Make;
import com.cct.repository.api.MakeRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MakeRepositoryImpl implements MakeRepository {

    private final String url = "https://www.autoevolution.com";

    @Override
    public Optional<Make> findOneById(String id) {
        return findAll()
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<Make> findAll() {
        try {
            return Jsoup
                    .connect(url + "/cars")
                    .get()
                    .body()
                    .select("[itemtype='https://schema.org/Brand']")
                    .stream()
                    .map(this::prepareMake)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to " + url);
        }
    }

    private Make prepareMake(Element e) {
        String id = e
                .select("h5 > a")
                .attr("href")
                .replace(url, "")
                .replace("/", "");
        String name = e.select("h5").text();
        String logoUrl = e.select("img").attr("src");
        return new Make(id, name, logoUrl);
    }
}
