package com.practice;


import com.practice.pojo.User;
import com.practice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserService userService;



    @Test
    public void finAllUser(){
        List<User> users =userService.findUser();
        System.out.println(users);
    }

}
