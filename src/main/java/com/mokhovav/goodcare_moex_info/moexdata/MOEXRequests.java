package com.mokhovav.goodcare_moex_info;

public class MOEXRequests {
    private final static String stockIndex = "https://iss.moex.com/iss/engines/stock/markets/index/securities.json?iss.meta=off&iss.only=securities&securities.columns=SECID,NAME,CURRENCYID";
    private final static String stockShares = "https://iss.moex.com/iss/engines/stock/markets/shares/securities.json?iss.meta=off&iss.only=securities&securities.columns=SECID,SECNAME,CURRENCYID";
    private final static String stockBonds = "https://iss.moex.com/iss/engines/stock/markets/bonds/securities.json?iss.meta=off&iss.only=securities&securities.columns=SECID,SHORTNAME,CURRENCYID";

    public static String getStockIndex() {
        return stockIndex;
    }

    public static String getStockShares() {
        return stockShares;
    }

    public static String getStockBonds() {
        return stockBonds;
    }
}
