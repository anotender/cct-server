package com.cct.controller;

import com.cct.model.dto.MakeDTO;
import com.cct.model.dto.ModelDTO;
import com.cct.service.api.MakeService;
import com.cct.service.api.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/makes")
public class MakeController {

    private final MakeService makeService;
    private final ModelService modelService;

    public MakeController(MakeService makeService, ModelService modelService) {
        this.makeService = makeService;
        this.modelService = modelService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MakeDTO> getMake(@PathVariable("id") String id) {
        return ResponseEntity.ok(makeService.getMake(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MakeDTO>> getMakes() {
        return ResponseEntity.ok(makeService.getMakes());
    }

    @GetMapping(value = "/{id}/models", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ModelDTO>> getModelsForMake(@PathVariable("id") String id) {
        return ResponseEntity.ok(modelService.getModelsByMakeId(id));
    }
}
