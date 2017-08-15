package com.cct.service.api;

import com.cct.model.dto.ModelDTO;

import java.util.Collection;

public interface ModelService {
    Collection<ModelDTO> getModelsByMakeId(String id);

    ModelDTO getModel(String id);
}
