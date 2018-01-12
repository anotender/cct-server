package com.cct.model.dto.google;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"open_now", "weekday_text"})
public class OpeningHoursDTO {

    @JsonProperty("open_now")
    private Boolean openNow;
    @JsonProperty("weekday_text")
    private List<Object> weekdayText = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("open_now")
    public Boolean getOpenNow() {
        return openNow;
    }

    @JsonProperty("open_now")
    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    @JsonProperty("weekday_text")
    public List<Object> getWeekdayText() {
        return weekdayText;
    }

    @JsonProperty("weekday_text")
    public void setWeekdayText(List<Object> weekdayText) {
        this.weekdayText = weekdayText;
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
