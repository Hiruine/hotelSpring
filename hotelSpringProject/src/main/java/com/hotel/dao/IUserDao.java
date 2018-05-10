package com.hotel.dao;

import com.hotel.model.User;

public interface IUserDao {


        User findByUsername(String username);

        void add(User entity);

}
