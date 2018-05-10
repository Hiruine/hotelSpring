package com.hotel.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.hotel")
public class JPAConfig {


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("hibernateProperties") Properties hibernateProperties,
            @Qualifier("datasourceProperties") Properties datasourceProperties
            ) {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();

        //injecting datasource into it
        em.setDataSource(dataSource(datasourceProperties));
        //packages where our entities will be
        em.setPackagesToScan("com.hotel.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties);
        em.setPersistenceUnitName("myPersistence");

        return em;
    }

    @Bean
    public DataSource dataSource(
            Properties datasourceProperties
    ){
        HikariConfig config = new HikariConfig(
                datasourceProperties);

        return new HikariDataSource(config);
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


    @Bean
    PropertiesFactoryBean datasourceProperties() {
        PropertiesFactoryBean properties = new PropertiesFactoryBean();
        properties.setLocation(new ClassPathResource("datasource.properties"));

        return properties;
    }
}
