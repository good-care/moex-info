package com.mokhovav.goodcare_moex_info.entites.assets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXData;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "columns",
        "securities"
})
public class AssetData {
    @JsonProperty("securities")
    private MOEXData<String> securities = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public MOEXData<String> getSecurities() {
        return securities;
    }

    public void setSecurities(MOEXData<String> securities) {
        this.securities = securities;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
