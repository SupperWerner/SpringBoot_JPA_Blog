package com.blog.blog.service.admin;

import com.blog.blog.po.User;

public interface UserService {
    User checkUser (String username, String password);
}
