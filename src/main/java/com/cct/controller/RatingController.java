package com.cct.controller;

import com.cct.model.dto.RatingDTO;
import com.cct.service.api.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> getRating(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ratingService.getRating(id));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> saveFuelRefill(@RequestBody RatingDTO ratingDTO) {
        RatingDTO savedRatingDTO = ratingService.save(ratingDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRatingDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedRatingDTO);
    }
}
