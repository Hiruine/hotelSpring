package com.hotel;

import com.hotel.config.JPAConfig;
import com.hotel.model.User;
import com.hotel.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class main {


    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        JPAConfig.class
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

