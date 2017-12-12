package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.Rating;
import com.cct.model.dto.RatingDTO;
import com.cct.repository.api.RatingRepository;
import com.cct.service.api.RatingService;
import com.cct.util.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.RATING_NOT_FOUND;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public RatingDTO getRating(Long id) {
        return ratingRepository
                .findOneById(id)
                .map(ModelMapper::convertToDTO)
                .orElseThrow(() -> new BadRequestException(RATING_NOT_FOUND));
    }

    @Override
    public Collection<RatingDTO> getRatingsForVersion(String id) {
        return ratingRepository
                .findByVersionId(id)
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        Rating rating = ModelMapper.convertToEntity(ratingDTO);
        return ModelMapper.convertToDTO(ratingRepository.save(rating));
    }
}
