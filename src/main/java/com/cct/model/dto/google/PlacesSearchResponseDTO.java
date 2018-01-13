package com.cct.model.dto.google;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "html_attributions",
        "results",
        "status"
})
public class PlacesSearchResponseDTO {

    @JsonProperty("html_attributions")
    private List<Object> htmlAttributions = null;
    @JsonProperty("results")
    private List<PlaceDTO> placeDTOs = null;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
