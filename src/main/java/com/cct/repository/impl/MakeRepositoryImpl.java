package com.cct.repository.impl;

import com.cct.model.Make;
import com.cct.repository.api.MakeRepository;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

@Repository
public class MakeRepositoryImpl implements MakeRepository {

    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;

    public MakeRepositoryImpl(RestTemplateBuilder restTemplateBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        this.httpEntity = new HttpEntity<>("parameters", headers);
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Optional<Make> findOneById(String id) {
        return findAll()
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public Collection<Make> findAll() {
        String response = restTemplate
                .exchange("https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes", GET, httpEntity, String.class)
                .getBody()
                .replace("?(", "")
                .replace(");", "");

        return new JSONObject(response)
                .getJSONArray("Makes")
                .toList()
                .stream()
                .map(Map.class::cast)
                .map(JSONObject::new)
                .map(this::prepareMake)
                .collect(Collectors.toList());
    }

    private Make prepareMake(JSONObject o) {
        return new Make(o.getString("make_id"), o.getString("make_display"));
    }
}
