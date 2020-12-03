package com.mokhovav.goodcare_moex_info.database;

public interface SQLSettings {
        String getDriverClassName();
        String getUrl();
        String getUsername();
        String getPassword();
        String[] getPackagesToScan();
        String getDDLAuto();
        String getDialect();
}
