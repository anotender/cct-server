package com.cct.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private Set<CarDTO> cars = new HashSet<>();
    private Set<RatingDTO> ratings = new HashSet<>();
}
