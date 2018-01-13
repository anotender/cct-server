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
        "result",
        "status"
})
public class PlaceDetailsResponseDTO {

    @JsonProperty("html_attributions")
    private List<Object> htmlAttributions = null;
    @JsonProperty("result")
    private PlaceDTO placeDTO;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
