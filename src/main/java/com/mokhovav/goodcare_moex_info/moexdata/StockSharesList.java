package com.mokhovav.goodcare_moex_info.moexdata;

import org.springframework.stereotype.Component;

@Component
public class StockSharesList {
    private DataFormat securities;

    public StockSharesList() {
    }

    public DataFormat getSecurities() {
        return securities;
    }

    public void setSecurities(DataFormat securities) {
        this.securities = securities;
    }
}
