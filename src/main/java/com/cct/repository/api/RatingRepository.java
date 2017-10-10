package com.cct.repository.api;

import com.cct.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findOneById(Long id);

    Collection<Rating> findByVersionId(String id);
}
