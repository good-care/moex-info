package com.mokhovav.goodcare_moex_info.entites.assetquotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mokhovav.goodcare_moex_info.moexdata.IntegerDataFormat;
import com.mokhovav.goodcare_moex_info.moexdata.StringDataFormat;

public class HistoryData {
    private StringDataFormat history;
    @JsonProperty("history.cursor")
    private IntegerDataFormat cursor;

    public HistoryData() {
    }

    public StringDataFormat getHistory() {
        return history;
    }

    public void setHistory(StringDataFormat history) {
        this.history = history;
    }

    public IntegerDataFormat getCursor() {
        return cursor;
    }

    public void setCursor(IntegerDataFormat cursor) {
        this.cursor = cursor;
    }
}
