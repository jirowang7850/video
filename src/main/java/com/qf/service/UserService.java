package com.qf.service;

import com.qf.pojo.User;

public interface UserService {

    User login(User user);

    User selectUserByEmail(String email);

    void updateUser(User user);

    boolean insertUser(User user);
}
