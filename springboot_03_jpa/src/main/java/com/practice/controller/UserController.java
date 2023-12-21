package com.practice.controller;


import com.practice.pojo.User;
import com.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //组合两个注解 @Controller + @ResponseBody
@RequestMapping("/user") //the path group name
public class UserController {

    @Autowired
    UserService userServiceImpl;
    @RequestMapping("/finAllUser")
    public List<User> finAll(){
        List<User> users =userServiceImpl.findUser();
        return users;
    }
}

