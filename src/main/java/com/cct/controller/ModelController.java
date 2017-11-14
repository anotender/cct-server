package com.cct.controller;

import com.cct.model.dto.ModelDTO;
import com.cct.model.dto.VersionDTO;
import com.cct.service.api.ModelService;
import com.cct.service.api.VersionService;
import com.cct.util.RequestParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/models")
public class ModelController {

    private final ModelService modelService;
    private final VersionService versionService;

    public ModelController(ModelService modelService, VersionService versionService) {
        this.modelService = modelService;
        this.versionService = versionService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ModelDTO>> getModels(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return ResponseEntity.ok(modelService.getModels(RequestParamsUtils.extractStringIdsFromParam(id)));
        }
        return ResponseEntity.ok(modelService.getModels());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelDTO> getModel(@PathVariable("id") String id) {
        return ResponseEntity.ok(modelService.getModel(id));
    }

    @GetMapping(value = "/{id}/versions", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VersionDTO>> getVersionsForModel(@PathVariable("id") String id) {
        return ResponseEntity.ok(versionService.getVersionsForModel(id));
    }
}
