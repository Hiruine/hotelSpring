package com.hotel.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:liquibaseDatasource.properties")
public class LiquibaseConfig {

    @Bean
    public SpringLiquibase liquibase(@Qualifier("liquibase.dataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        return liquibase;
    }

    @Bean(name = "liquibase.dataSource")
    public DataSource dataSource(LiquibaseDatasourceProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriver());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());


        return dataSource;
    }

    @Component
    static class LiquibaseDatasourceProperties {

        @Value("${liquibase.url}")
        private String url;
        @Value("${liquibase.dataSourceClassName}")
        private String driver;
        @Value("${liquibase.username}")
        private String username;
        @Value("${liquibase.password}")
        private String password;

        public String getUrl() {
            return url;
        }

        public String getDriver() {
            return driver;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
