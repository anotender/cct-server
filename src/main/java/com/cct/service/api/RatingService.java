package com.cct.service.api;

import com.cct.model.dto.RatingDTO;

import java.util.Collection;

public interface RatingService {
    RatingDTO getRating(Long id);

    Collection<RatingDTO> getRatingsForVersion(String id);

    RatingDTO save(RatingDTO ratingDTO);
}
