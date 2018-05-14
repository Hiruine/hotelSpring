package com.hotel;

import com.hotel.config.JpaConfig;
import com.hotel.config.LiquibaseConfig;
import com.hotel.model.User;
import com.hotel.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {


    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        LiquibaseConfig.class, JpaConfig.class
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

