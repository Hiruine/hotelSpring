package com.hotel.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Import(DatasourceConfig.class)
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase liquibase(@Qualifier("liquibase.dataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        return liquibase;
    }

    @Bean("liquibase.dataSource")
    public DataSource dataSource(LiquibaseProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        return dataSource;
    }

//    @Bean("liquibase.dataSource.properties")
//    public PropertiesFactoryBean dataSourceProperties() {
//        PropertiesFactoryBean properties = new PropertiesFactoryBean();
//        properties.setLocation(new ClassPathResource("datasource.properties"));
//
//        return properties;
//    }
//
    @Component
    static class LiquibaseProperties {

        @Value("dataSource.user")
        private String url;
        private String username;
        private String passwor;

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }
    }
}
