package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Model;
import com.cct.model.dto.ModelDTO;
import com.cct.repository.api.ModelRepository;
import com.cct.repository.api.ModelWebRepository;
import com.cct.service.api.ModelService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.MODEL_NOT_FOUND;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelWebRepository modelWebRepository;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepository modelRepository, ModelWebRepository modelWebRepository, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.modelWebRepository = modelWebRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<ModelDTO> getModelsByMakeId(String id) {
        Collection<Model> models = modelRepository.findByMakeId(id);

        if (models.isEmpty()) {
            models = modelWebRepository.findByMakeId(id);
            modelRepository.save(models);
        }

        return models
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public ModelDTO getModel(String id) {
        Optional<Model> model;

        if (modelRepository.exists(id)) {
            model = modelRepository.findOneById(id);
        } else {
            model = modelWebRepository.findOneById(id);
            model.ifPresent(modelRepository::save);
        }

        return model
                .map(modelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(MODEL_NOT_FOUND));
    }
}
