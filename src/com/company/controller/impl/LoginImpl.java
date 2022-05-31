package com.company.controller.impl;

import com.company.controller.Login;
import com.company.model.User;
import com.company.service.UserManager;

public class LoginImpl implements Login {
    private final UserManager userManager;

    public LoginImpl(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public User execute(String username, String password) {
        User user = userManager.findUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        System.out.println("Nhập sai thông tin. Vui lòng nhập lại!!!");
        return null;
    }
}
