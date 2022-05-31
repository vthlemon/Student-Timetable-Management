package com.company.service.impl;

import com.company.constant.Constant;
import com.company.model.User;
import com.company.service.UserManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManagerImpl implements UserManager {
    private final Map<String, User> userMap = new HashMap<>();
    private static final String FILE_NAME = "users.txt";
    private static final String FILE_PATH = Constant.DATA_PATH + FILE_NAME;

    private boolean initialize = false;

    @Override
    public void initData() {
        if (initialize) {
            return;
        }

        initialize = true;

        try {
            Reader reader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] items = line.split(Constant.SEPARATE);
                    String username = items[0];
                    String password = items[1];

                    if (items.length == 2) {
                        User user = new User(username, password);
                        userMap.put(username, user);
                    }

                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("File not found");
        }
    }

    @Override
    public void saveData() {
        try {
            Writer writer = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (User user : userMap.values()) {
                bufferedWriter.write(user.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        userMap.put(user.getUsername(), user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMap.get(username);
    }
}
