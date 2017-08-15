package com.cct.repository.impl;

import com.cct.model.Model;
import com.cct.repository.api.ModelRepository;
import com.cct.util.HttpUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ModelRepositoryImpl implements ModelRepository {

    private final HttpUtils httpUtils;

    public ModelRepositoryImpl(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Override
    public Optional<Model> findOneById(String id) {
        JSONArray a = httpUtils.getJSONArray("https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getModel&model=" + id);

        if (a.length() != 1) {
            return Optional.empty();
        }

        JSONObject o = (JSONObject) a.get(0);

        if (!o.has("model_id")) {
            return Optional.empty();
        }

        return Optional.of(prepareModel(o));
    }

    @Override
    public Collection<Model> findByMakeId(String id) {
        return httpUtils
                .getJSONObject("https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getTrims&make=" + id)
                .getJSONArray("Trims")
                .toList()
                .stream()
                .map(Map.class::cast)
                .map(JSONObject::new)
                .map(this::prepareModel)
                .collect(Collectors.toSet());
    }

    private Model prepareModel(JSONObject o) {
        String modelId = o.getString("model_id");
        String makeId = o.getString("model_make_id");
        String name = o.getString("model_name");
        String version = o.getString("model_trim");
        int year = o.getInt("model_year");

        double highwayFuelConsumption = 0;
        if (o.has("model_lkm_hwy") && !o.isNull("model_lkm_hwy")) {
            highwayFuelConsumption = o.getDouble("model_lkm_hwy");
        }

        double cityFuelConsumption = 0;
        if (o.has("model_lkm_city") && !o.isNull("model_lkm_city")) {
            cityFuelConsumption = o.getDouble("model_lkm_city");
        }

        double mixedFuelConsumption = 0;
        if (o.has("model_lkm_mixed") && !o.isNull("model_lkm_mixed")) {
            mixedFuelConsumption = o.getDouble("model_lkm_mixed");
        }

        return new Model(
                modelId, makeId, name, version, year,
                highwayFuelConsumption, cityFuelConsumption, mixedFuelConsumption
        );
    }
}
