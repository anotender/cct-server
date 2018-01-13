package com.cct.model.dto.google;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"open_now", "weekday_text"})
public class OpeningHoursDTO {

    @JsonProperty("open_now")
    private Boolean openNow;
    @JsonProperty("weekday_text")
    private List<Object> weekdayText = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
