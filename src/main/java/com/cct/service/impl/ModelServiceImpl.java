package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.dto.ModelDTO;
import com.cct.repository.api.ModelRepository;
import com.cct.service.api.ModelService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.MODEL_NOT_FOUND;

@Service
@Transactional
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepository modelRepository, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<ModelDTO> getModelsByMakeId(String id) {
        return modelRepository
                .findByMakeIdOrderByName(id)
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModelDTO getModel(String id) {
        return modelRepository
                .findOneById(id)
                .map(modelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(MODEL_NOT_FOUND));
    }
}
