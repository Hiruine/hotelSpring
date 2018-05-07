package com.hotel.persistance.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "username")
    final String username;

    @Column(password = "password")
    String password;

    public User(String password) {
        this.password = password;
    }

}
