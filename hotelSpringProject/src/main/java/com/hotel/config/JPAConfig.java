package com.hotel.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.hotel")
@Import(DatasourceConfig.class)
public class JPAConfig {

    private DatasourceConfig datasourceConfig;

    @Autowired
    public JPAConfig(DatasourceConfig datasourceConfig) {
        this.datasourceConfig = datasourceConfig;
    }

    @Bean
    @DependsOn("liquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("hibernateProperties") Properties hibernateProperties,
            @Qualifier("dmlDatasourceProperties") Properties datasourceProperties
            ) {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();

        //injecting datasource into it
        em.setDataSource(datasourceConfig
                .dmlDataSource(datasourceProperties));
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



}
