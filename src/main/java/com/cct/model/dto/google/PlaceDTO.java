package com.cct.model.dto.google;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "geometry",
        "icon",
        "id",
        "name",
        "opening_hours",
        "place_id",
        "rating",
        "reference",
        "scope",
        "types",
        "vicinity",
        "price_level"
})
public class PlaceDTO {

    @JsonProperty("geometry")
    private GeometryDTO geometryDTO;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("opening_hours")
    private OpeningHoursDTO openingHoursDTO;
    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("rating")
    private Integer rating;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("types")
    private List<String> types = null;
    @JsonProperty("vicinity")
    private String vicinity;
    @JsonProperty("price_level")
    private Integer priceLevel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
