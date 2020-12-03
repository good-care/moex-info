package com.mokhovav.goodcare_moex_info.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SQLSettingsPostgresSQL implements SQLSettings, SQLAdditionalSettings {

    private final Environment environment;

    public SQLSettingsPostgresSQL(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getDriverClassName() {
        return environment.getProperty("hibernate.driver-class-name");
    }

    @Override
    public String getUrl() {
        return environment.getProperty("hibernate.prefix") + "://" +
                environment.getProperty("hibernate.host") +
                ":" + environment.getProperty("hibernate.port", Integer.class) +
                "/" + environment.getProperty("hibernate.database");
    }

    @Override
    public String getUsername() {
        return environment.getProperty("hibernate.username");
    }

    @Override
    public String getPassword() {
        return environment.getProperty("hibernate.password");
    }

    @Override
    public String[] getPackagesToScan() {
        return new String[]{
                environment.getProperty("hibernate.entities.moex")
        };
    }

    @Override
    public String getDDLAuto() {
        return environment.getProperty("hibernate.ddl-auto");
    }

    @Override
    public String getDialect() {
        return environment.getProperty("hibernate.sql-dialect");
    }

    @Override
    public String getTimeZone() {
        return environment.getProperty("hibernate.timezone");
    }
}
