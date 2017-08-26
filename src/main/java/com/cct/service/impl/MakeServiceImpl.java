package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.dto.MakeDTO;
import com.cct.repository.api.MakeRepository;
import com.cct.service.api.MakeService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.MAKE_NOT_FOUND;

@Service
@Transactional
public class MakeServiceImpl implements MakeService {

    private final MakeRepository makeRepository;
    private final ModelMapper modelMapper;

    public MakeServiceImpl(MakeRepository makeRepository, ModelMapper modelMapper) {
        this.makeRepository = makeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MakeDTO getMake(String id) {
        return makeRepository
                .findOneById(id)
                .map(modelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(MAKE_NOT_FOUND));
    }

    @Override
    public Collection<MakeDTO> getMakes() {
        return makeRepository
                .findAll()
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
