package com.qf.service.impl;

import com.qf.dao.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Override
    public User login(User user) {
        return mapper.login(user);
    }

    @Override
    public User selectUserByEmail(String email) {
        return mapper.selectUserByEmail(email);
    }

    @Override
    public void updateUser(User user) {
        mapper.updateUser(user);
    }

    @Override
    public boolean insertUser(User user) {
        return mapper.insertUser(user);
    }
}
