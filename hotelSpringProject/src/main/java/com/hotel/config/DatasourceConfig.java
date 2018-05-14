package com.hotel.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class DatasourceConfig {


    @Bean(name = "dmlDataSource")
    public DataSource dmlDataSource(
            @Qualifier("dmlDatasourceProperties")
                    Properties datasourceProperties
    ) {
        HikariConfig config = new HikariConfig(datasourceProperties);

        return new HikariDataSource(config);
    }

    @Bean
    PropertiesFactoryBean dmlDatasourceProperties() {
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocation(new ClassPathResource("datasource.properties"));

        return properties;
    }

}
