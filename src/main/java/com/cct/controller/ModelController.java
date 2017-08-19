package com.cct.controller;

import com.cct.model.dto.ModelDTO;
import com.cct.service.api.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/models")
public class ModelController {

    private final ModelService modelService;


    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelDTO> getModel(@PathVariable("id") String id) {
        return ResponseEntity.ok(modelService.getModel(id));
    }
}
