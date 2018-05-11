package com.hotel.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@Import(JPAConfig.class)
public class LiquibaseConfig {

    private JPAConfig jpaConfig;

    @Autowired
    public LiquibaseConfig(JPAConfig jpaConfig) {
        this.jpaConfig = jpaConfig;
    }

    @Bean
    public SpringLiquibase liquibase(
            @Qualifier("datasourceProperties") Properties datasourceProperties) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setDataSource(jpaConfig.dataSource(datasourceProperties));
        return liquibase;
    }


}
