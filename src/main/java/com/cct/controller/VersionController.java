package com.cct.controller;

import com.cct.model.dto.RatingDTO;
import com.cct.model.dto.VersionDTO;
import com.cct.service.api.RatingService;
import com.cct.service.api.VersionService;
import com.cct.util.RequestParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/versions")
public class VersionController {

    private static final String ORDER_BY_POPULARITY_PARAM = "orderByPopularity";

    private final VersionService versionService;
    private final RatingService ratingService;

    public VersionController(VersionService versionService, RatingService ratingService) {
        this.versionService = versionService;
        this.ratingService = ratingService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VersionDTO>> getVersions(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        Collection<VersionDTO> versions = null;

        if (StringUtils.isNotBlank(id)) {
            versions = versionService.getVersions(RequestParamsUtils.extractStringIdsFromParam(id));
        }

        if (ORDER_BY_POPULARITY_PARAM.equalsIgnoreCase(search)) {
            if (versions == null) {
                versions = versionService.getVersionsOrderByPopularity();
            } else {
                versions = versionService.getVersionsOrderByPopularity(versions);
            }
        }

        if (versions == null) {
            versions = versionService.getVersions();
        }

        if (limit != null) {
            versions = versions
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(versions);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<VersionDTO> getVersion(@PathVariable("id") String id) {
        return ResponseEntity.ok(versionService.getVersion(id));
    }

    @GetMapping(value = "/{id}/ratings", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RatingDTO>> getRatingsForVersion(@PathVariable("id") String id) {
        return ResponseEntity.ok(ratingService.getRatingsForVersion(id));
    }
}
