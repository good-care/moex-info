package com.mokhovav.goodcare_moex_info.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "hibernate")
@Validated
public class HibernateProperties {
    private String prefix;
    private String sql_dialect;
    private String driver_class_name;
    private String ddl_auto;
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String[] entities;

    public String getUrl() {
        return getPrefix() + "://" + getHost() + ":" + getPort() + "/" + getDatabase();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSql_dialect() {
        return sql_dialect;
    }

    public void setSql_dialect(String sql_dialect) {
        this.sql_dialect = sql_dialect;
    }

    public String getDriver_class_name() {
        return driver_class_name;
    }

    public void setDriver_class_name(String driver_class_name) {
        this.driver_class_name = driver_class_name;
    }

    public String getDdl_auto() {
        return ddl_auto;
    }

    public void setDdl_auto(String ddl_auto) {
        this.ddl_auto = ddl_auto;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getEntities() {
        return entities;
    }

    public void setEntities(String[] entities) {
        this.entities = entities;
    }
}
