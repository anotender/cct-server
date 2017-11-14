package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Model;
import com.cct.model.Version;
import com.cct.model.dto.VersionDTO;
import com.cct.repository.api.ModelRepository;
import com.cct.repository.api.VersionRepository;
import com.cct.repository.api.VersionWebRepository;
import com.cct.service.api.VersionService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.MODEL_NOT_FOUND;
import static com.cct.exception.ErrorInfo.VERSION_NOT_FOUND;

@Service
@Transactional
public class VersionServiceImpl implements VersionService {

    private final ModelRepository modelRepository;
    private final VersionRepository versionRepository;
    private final VersionWebRepository versionWebRepository;
    private final ModelMapper modelMapper;

    public VersionServiceImpl(ModelRepository modelRepository, VersionRepository versionRepository, VersionWebRepository versionWebRepository, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.versionRepository = versionRepository;
        this.versionWebRepository = versionWebRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VersionDTO getVersion(String versionId) {
        Optional<Version> version = versionRepository.findOneById(versionId);

        if (version.isPresent() && !isFullyFetched(version.get())) {
            versionWebRepository.fetchData(version.get());
        }

        return version
                .map(modelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(VERSION_NOT_FOUND));
    }

    @Override
    public Collection<VersionDTO> getVersions() {
        return versionRepository
                .findAll()
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<VersionDTO> getVersions(Collection<String> ids) {
        return ids
                .stream()
                .map(this::getVersion)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<VersionDTO> getVersionsOrderByPopularity() {
        return getVersionsOrderByPopularity(getVersions());
    }

    @Override
    public Collection<VersionDTO> getVersionsOrderByPopularity(Collection<VersionDTO> versions) {
        return versions
                .stream()
                .sorted((v1, v2) -> Long.compare(v2.getCars().size(), v1.getCars().size()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<VersionDTO> getVersionsForModel(String modelId) {
        Collection<Version> versions = versionRepository.findByModelId(modelId);

        if (versions.isEmpty()) {
            Model model = modelRepository
                    .findOneById(modelId)
                    .orElseThrow(() -> new BadRequestException(MODEL_NOT_FOUND));
            versions = versionWebRepository
                    .findByMakeIdAndModelId(model.getMake().getId(), modelId)
                    .stream()
                    .peek(v -> v.setModel(model))
                    .sorted(Comparator.comparing(Version::getYears))
                    .collect(Collectors.toList());
            versionRepository.save(versions);
        }

        return versions
                .stream()
                .map(modelMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean isFullyFetched(Version v) {
        return v.getCityFuelConsumption() != null && v.getHighwayFuelConsumption() != null && v.getMixedFuelConsumption() != null;
    }
}
