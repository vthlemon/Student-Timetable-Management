package com.company.controller.impl;

import com.company.controller.ChangePassword;
import com.company.model.User;
import com.company.service.UserManager;

public class ChangePasswordImpl implements ChangePassword {
    private final UserManager userManager;

    public ChangePasswordImpl(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public boolean execute(String username, String password, String rePassword) {
        if (password.equals(rePassword)){
            User user = userManager.findUserByUsername(username);
            if (user == null) {
                return false;
            }

            user.setPassword(password);
            return true;
        }

        return false;
    }
}
