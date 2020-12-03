package com.mokhovav.goodcare_moex_info.entites.assets;

import com.mokhovav.goodcare_moex_info.moexdata.StringDataFormat;

public class SecurityData {
    private StringDataFormat securities;

    public SecurityData() {
    }

    public StringDataFormat getSecurities() {
        return securities;
    }

    public void setSecurities(StringDataFormat securities) {
        this.securities = securities;
    }
}
