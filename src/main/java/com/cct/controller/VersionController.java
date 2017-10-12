package com.cct.controller;

import com.cct.model.dto.RatingDTO;
import com.cct.model.dto.VersionDTO;
import com.cct.service.api.RatingService;
import com.cct.service.api.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/versions")
public class VersionController {

    private final VersionService versionService;
    private final RatingService ratingService;

    public VersionController(VersionService versionService, RatingService ratingService) {
        this.versionService = versionService;
        this.ratingService = ratingService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VersionDTO>> getVersions(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        Collection<VersionDTO> versions;

        if ("orderByPopularity".equalsIgnoreCase(search)) {
            versions = versionService.getVersionsOrderByPopularity();
        } else {
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
