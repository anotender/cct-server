package com.cct.model.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class MakeDTO {
    private String id;
    private String name;
    private String logoUrl;
    private Set<String> models = new HashSet<>();
}
