package com.cct.service.api;

import com.cct.model.dto.ModelDTO;

import java.util.Collection;

public interface ModelService {
    Collection<ModelDTO> getModels();

    Collection<ModelDTO> getModels(Collection<String> ids);

    Collection<ModelDTO> getModelsByMakeId(String id);

    ModelDTO getModel(String id);
}
