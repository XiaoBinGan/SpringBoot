package com.practice.controller;


import com.practice.pojo.User;
import com.practice.service.UserService;
import com.practice.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/user") //分配请求组前缀相于二级路径
public class UserController {

    @Autowired
    UserService userService;


    /**
     * function name findAll
     * explain
     * find all user from the service -> dao
     * @return all user information
     */

    @RequestMapping("/findAllUser")
    public Result findAll(){
        List<User> list = userService.findUser();
        return Result.ok(list);
    }



}
