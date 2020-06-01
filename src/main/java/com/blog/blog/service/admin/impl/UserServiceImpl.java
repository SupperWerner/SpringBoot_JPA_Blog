package com.blog.blog.service.admin.impl;

import com.blog.blog.dao.UserRepository;
import com.blog.blog.po.User;
import com.blog.blog.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }
}

