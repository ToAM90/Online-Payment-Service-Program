package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    long findAccountByUser(long userId);

    List<User> findAll(long id);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}
