package com.hotel.services;

import com.hotel.dao.IUserDao;
import com.hotel.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao){
        this.userDao = userDao;
    }

    @Transactional
    public void add(final User entity) {
        userDao.add(entity);
    }

    public User findByUsername(String id) {
        return userDao.findByUsername(id);
    }

}
