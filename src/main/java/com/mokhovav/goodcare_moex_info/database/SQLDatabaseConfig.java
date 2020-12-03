package com.mokhovav.goodcare_moex_info.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class SQLDatabaseConfig {

    private final SQLSettings sqlSettings;
    private final SQLAdditionalSettings sqlAdditionalSettings;

    public SQLDatabaseConfig(SQLSettings sqlSettings, SQLAdditionalSettings sqlAdditionalSettings) {
        this.sqlSettings = sqlSettings;
        this.sqlAdditionalSettings = sqlAdditionalSettings;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(sqlSettings.getDriverClassName());
        dataSource.setUrl(sqlSettings.getUrl());
        dataSource.setUsername(sqlSettings.getUsername());
        dataSource.setPassword(sqlSettings.getPassword());
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory (){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(sqlSettings.getPackagesToScan());
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(hibernateTransactionManager());
    }

    private final Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", sqlSettings.getDDLAuto());
        properties.setProperty("hibernate.dialect", sqlSettings.getDialect());
        return properties;
    }
}
