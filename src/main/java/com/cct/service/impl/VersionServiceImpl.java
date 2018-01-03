package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Model;
import com.cct.model.Version;
import com.cct.model.dto.VersionDTO;
import com.cct.repository.api.ModelRepository;
import com.cct.repository.api.VersionRepository;
import com.cct.service.api.VersionAutoEvolutionService;
import com.cct.service.api.VersionService;
import com.cct.util.AutoEvolutionUtils;
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
    private final VersionAutoEvolutionService versionAutoEvolutionService;

    public VersionServiceImpl(ModelRepository modelRepository, VersionRepository versionRepository, VersionAutoEvolutionService versionAutoEvolutionService) {
        this.modelRepository = modelRepository;
        this.versionRepository = versionRepository;
        this.versionAutoEvolutionService = versionAutoEvolutionService;
    }

    @Override
    public VersionDTO getVersion(String versionId) {
        Optional<Version> version = versionRepository.findOneById(versionId);

        if (version.isPresent() && !isFullyFetched(version.get())) {
            versionAutoEvolutionService
                    .getCommonVersions(version.get().getId())
                    .forEach(this::updateFuelConsumption);
        }

        return version
                .map(ModelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(VERSION_NOT_FOUND));
    }

    @Override
    public Collection<VersionDTO> getVersions() {
        return versionRepository
                .findAll()
                .stream()
                .map(ModelMapper::convertToDTO)
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
            versions = versionAutoEvolutionService
                    .getVersionsForMakeAndModel(model.getMake().getId(), modelId)
                    .stream()
                    .map(ModelMapper::convertToEntity)
                    .peek(v -> v.setModel(model))
                    .sorted(Comparator.comparing(Version::getYears))
                    .collect(Collectors.toList());
            versionRepository.save(versions);
        }

        return versions
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    private boolean isFullyFetched(Version v) {
        return v.getCityFuelConsumption() != null && v.getHighwayFuelConsumption() != null && v.getMixedFuelConsumption() != null;
    }

    private void updateFuelConsumption(VersionDTO versionDTO) {
        Version version = versionRepository
                .findOneById(AutoEvolutionUtils.encodeId(versionDTO.getId()))
                .orElseThrow(() -> new BadRequestException(VERSION_NOT_FOUND));

        version.setCityFuelConsumption(versionDTO.getCityFuelConsumption());
        version.setHighwayFuelConsumption(versionDTO.getHighwayFuelConsumption());
        version.setMixedFuelConsumption(versionDTO.getMixedFuelConsumption());
    }
}
