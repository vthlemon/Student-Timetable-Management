package com.company.service;

import com.company.model.User;

public interface UserManager {
    void initData();
    void saveData();

    void saveUser(User user);
    User findUserByUsername(String username);
}
