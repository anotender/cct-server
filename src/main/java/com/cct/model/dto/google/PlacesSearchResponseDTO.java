package com.cct.model.dto.google;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("html_attributions")
    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    @JsonProperty("html_attributions")
    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    @JsonProperty("results")
    public List<PlaceDTO> getPlaceDTOs() {
        return placeDTOs;
    }

    @JsonProperty("results")
    public void setPlaceDTOs(List<PlaceDTO> placeDTOs) {
        this.placeDTOs = placeDTOs;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
