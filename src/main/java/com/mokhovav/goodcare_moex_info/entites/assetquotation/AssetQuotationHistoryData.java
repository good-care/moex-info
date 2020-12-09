package com.mokhovav.goodcare_moex_info.entites.assetquotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mokhovav.goodcare_moex_info.moexdata.MOEXData;

import java.util.HashMap;
import java.util.Map;

public class AssetQuotationHistoryData {
    @JsonProperty("history")
    private MOEXData<String> history = null;
    @JsonProperty("history.cursor")
    private MOEXData<Integer> cursor = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public MOEXData<String> getHistory() {
        return history;
    }

    public void setHistory(MOEXData<String> history) {
        this.history = history;
    }

    public MOEXData<Integer> getCursor() {
        return cursor;
    }

    public void setCursor(MOEXData<Integer> cursor) {
        this.cursor = cursor;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
