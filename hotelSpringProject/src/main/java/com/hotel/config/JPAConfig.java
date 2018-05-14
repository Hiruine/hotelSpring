package com.hotel.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.hotel")
@PropertySource("classpath:jpaDatasource.properties")
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("hibernateProperties") Properties hibernateProperties,
            @Qualifier("jpa.dataSource") DataSource dataSource
            ) {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();

        //injecting datasource into it
        em.setDataSource(dataSource);
        //packages where our entities will be
        em.setPackagesToScan("com.hotel.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties);
        em.setPersistenceUnitName("myPersistence");

        return em;
    }




    //transaction manager, injecting the LocalContainerEntityManagerFactoryBean into it
    @Bean
    public JpaTransactionManager transactionManager(
            EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    PropertiesFactoryBean hibernateProperties() {
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocation(new ClassPathResource("hibernate.properties"));

        return properties;
    }

    @Bean("jpa.dataSource")
    public DataSource dataSource(JpaDatasourceProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriver());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());



        return dataSource;
    }


    @Component
    static class JpaDatasourceProperties {

        @Value("${jpa.url}")
        private String url;
        @Value("${jpa.dataSourceClassName}")
        private String driver;
        @Value("${jpa.username}")
        private String username;
        @Value("${jpa.password}")
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
