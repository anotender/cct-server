package com.cct.repository.impl;

import com.cct.model.Make;
import com.cct.repository.api.MakeRepository;
import com.cct.util.HttpUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MakeRepositoryImpl implements MakeRepository {

    private final HttpUtils httpUtils;

    public MakeRepositoryImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
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
        return httpUtils
                .getJSONObject("https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes")
                .getJSONArray("Makes")
                .toList()
                .stream()
                .map(Map.class::cast)
                .map(JSONObject::new)
                .map(this::prepareMake)
                .collect(Collectors.toList());
    }

    private Make prepareMake(JSONObject o) {
        return new Make(
                o.getString("make_id"),
                o.getString("make_display"),
                o.getString("make_country")
        );
    }
}
