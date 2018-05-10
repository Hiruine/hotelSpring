package com.hotel.dao;


import com.hotel.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao implements IUserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsername(String username) {
        return entityManager.find(User.class, username);
    }

    @Override
    public void add(User entity) {
        entityManager.persist(entity);
    }
}
