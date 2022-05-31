package com.company.controller;

import com.company.model.User;

public interface Login {
    User execute(String username, String password);
}
