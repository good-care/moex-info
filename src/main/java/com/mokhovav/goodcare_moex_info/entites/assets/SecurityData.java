package com.mokhovav.goodcare_moex_info.moexdata;

import org.springframework.stereotype.Component;

@Component
public class SecurityList {
    private DataFormat securities;

    public SecurityList() {
    }

    public DataFormat getSecurities() {
        return securities;
    }

    public void setSecurities(DataFormat securities) {
        this.securities = securities;
    }
}
