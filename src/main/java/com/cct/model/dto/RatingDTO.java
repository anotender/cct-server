package com.cct.model.dto;

import lombok.Data;

@Data
public class RatingDTO {
    private Long id;
    private Integer points;
    private String comment;
    private String versionId;
    private Long userId;
}
