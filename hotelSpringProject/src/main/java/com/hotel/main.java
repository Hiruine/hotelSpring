package com.hotel;

import com.hotel.config.DatasourceConfig;
import com.hotel.config.JPAConfig;
import com.hotel.config.LiquibaseConfig;
import com.hotel.model.User;
import com.hotel.services.UserService;
import liquibase.Liquibase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class main {


    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        DatasourceConfig.class, LiquibaseConfig.class, JPAConfig.class
                );

        UserService userService =
                context.getBean(UserService.class);

        userService.add(
                new User("hiruine",
                         "password"));

//        System.out.println(studentService
//                .findByUsername("hiruine"));
    }
}

