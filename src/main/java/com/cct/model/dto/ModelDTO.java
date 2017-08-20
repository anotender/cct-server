package com.cct.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class ModelDTO {
    private String id;
    private String makeId;
    private String name;
    private String body;
    private Set<String> versions = new HashSet<>();
}
