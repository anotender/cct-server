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
        double highwayFuelConsumption = computeFuelConsumption(o, "hwy");
        double cityFuelConsumption = computeFuelConsumption(o, "city");
        double mixedFuelConsumption = computeFuelConsumption(o, "mixed");

        return new Model(
                modelId, makeId, name, version, year,
                highwayFuelConsumption, cityFuelConsumption, mixedFuelConsumption
        );
    }

    private double computeFuelConsumption(JSONObject o, String type) {
        String lkm = "model_lkm_" + type;
        String mpg = "model_mpg_" + type;

        if (o.has(lkm) && o.has(mpg)) {
            return o.getDouble(lkm) > o.getDouble(mpg) ? o.getDouble(mpg) : o.getDouble(lkm);
        } else if (o.has(lkm)) {
            return o.getDouble(lkm);
        } else if (o.has(mpg)) {
            return 235 / o.getDouble(mpg);
        }
        return 0;
    }
}
