package com.practice.controller;

import com.practice.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

//组合两个注解 @Controller + @ResponseBody
@RestController
//@Controller
@RequestMapping(path ="/user")
public class UserController {

    @RequestMapping(path = "/findAll")
//    @ResponseBody 通过消息转换器HttpMessageConverter将返回的Bean 对象转换成json字符串
    public List<User> finAll(HttpServletRequest request){
        List<User> userList = new ArrayList<User>(); // 模拟用户列表
        User user1= new User();
        user1.setUsername("golang");
        user1.setAge(18);
        user1.setPassword("123456");
        user1.setSex("未知");
        userList.add(user1);
        return userList;
    }

    /**
     * 这段逻辑我们用来测试后面的http拦截器是否生效
     *  public enum RequestMethod {
     *      GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
     *  }
     */
    @RequestMapping(path = "/registry",method = GET)
    public void registryUser(HttpServletRequest request){
            String requestPath = request.getRequestURI(); // 获取请求的路径
            System.out.println("Request Path: " + requestPath);
            return;
    }

}
